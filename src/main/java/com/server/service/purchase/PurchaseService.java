package com.server.service.purchase;

import com.server.dto.purchase.RequestPurchaseDto;
import com.server.entity.payment.PaymentMethod;
import com.server.entity.purchase.OrderStatus;
import com.server.entity.purchase.Purchase;
import com.server.entity.product.Goods;
import com.server.entity.product.Services;
import com.server.entity.user.ApiUsers;
import com.server.entity.user.Role;
import com.server.repository.purchase.PurchaseRepository;
import com.server.service.payment.IPaymentService;
import com.server.service.payment.paymentServiceFactory.PaymentServiceFactory;
import com.server.service.product.GoodsService;
import com.server.service.product.ServiceForServices;
import com.server.service.user.apiUserService.ApiUsersService;
import com.server.service.user.userServiceFacade.ClientServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с заказами
 */
@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final ClientServiceFacade clientServiceFacade;

    private final ApiUsersService apiUsersService;

    private final GoodsService goodsService;

    private final ServiceForServices serviceForServices;

    private final PaymentServiceFactory paymentServiceFactory;


    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository,
                           ClientServiceFacade clientServiceFacade,
                           ApiUsersService apiUsersService,
                           GoodsService goodsService,
                           ServiceForServices serviceForServices,
                           PaymentServiceFactory paymentServiceFactory) {
        this.purchaseRepository = purchaseRepository;
        this.clientServiceFacade = clientServiceFacade;
        this.apiUsersService = apiUsersService;
        this.goodsService = goodsService;
        this.serviceForServices = serviceForServices;
        this.paymentServiceFactory = paymentServiceFactory;
    }


    /**
     * Публичный метод для сериализации данных одного объекта,
     * полученного из базы данных в Dto для передачи на контроллер
     * @return Вернет Dto объект
     */
    public RequestPurchaseDto serialisInDtoObject(Purchase purchase){

        return new RequestPurchaseDto(
                purchase.getId(),
                purchase.getGoodsList(),
                purchase.getServicesList(),
                purchase.getPurchaseAmount(),
                purchase.getStatus(),
                purchase.getClient().getId()
        );

    }


    /**
     * Публичный метод для сериализации данных списка объектов,
     * полученных из базы данных в Dto для передачи на контроллер
     * @return Вернет список Dto объектов
     */
    public List<RequestPurchaseDto> serialisInListDtoObject(List<Purchase> purchaseList){
        List<RequestPurchaseDto> purchaseDtoList = new ArrayList<>();

        for(Purchase purchase : purchaseList){

            purchaseDtoList.add(new RequestPurchaseDto(
                    purchase.getId(),
                    purchase.getGoodsList(),
                    purchase.getServicesList(),
                    purchase.getPurchaseAmount(),
                    purchase.getStatus(),
                    purchase.getClient().getId()
            ));

        }

        return purchaseDtoList;

    }


    /**
     * Метод получает из SecurityContext авторизованного пользователя и создает заказ
     * с привязкой к авторизованному пользователю
     * @param goodsId список товаров
     * @param servicesId список услуг
     * @return Вернет созданный заказ
     */
    public Purchase create(int[] goodsId, int[] servicesId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return this.purchaseCreater(goodsId, servicesId, authentication.getName());

    }


    /**
     * Метод создает заказ для указанного пользователя
     * с привязкой к авторизованному пользователю
     * @param goodsId список товаров
     * @param servicesId список услуг
     * @param phone Телефон клиента
     * @return Вернет созданный заказ
     */
    public Purchase manualCreate(int[] goodsId, int[] servicesId, String phone) {

            return this.purchaseCreater(goodsId, servicesId, phone);

    }


    /**
     * Метод проверяет роль авторизованного пользователя и возвращает ссписок всех заказов для модератора
     * и список заказов конкретного клиента для пользователя с ролью Клиент
     * @return Вернет список заказов
     */
    public List<Purchase> readAll() {

        /*
         * Получаем данные авторизованного пользователя
         */

        ApiUsers apiUsers = this.getApiUsers();

        Role role = apiUsers.getRole();

        /*
         * Если роль Админ - возвращаем список всех заказов
         * Иначе - список заказов, принадлежащих авторизованному пользователю
         */

        if(role == Role.ADMIN){

            return purchaseRepository.findAll();

        } else {

            return purchaseRepository.findAllByClient(clientServiceFacade.findByPhone(apiUsers.getPhone()));

        }

    }


    /**
     * Метод возвращает информацию о конткретном заказе,
     * нет ограничений для пользователя с ролью Админ
     * для пользователя с ролью Клиент производится проверка, что заказ принаждлежит авторизщованному клиенту
     *      - Принимает id искомого заказа
     * @param id идентификатор заказа
     * @return Вернет заказ
     */
    public Purchase readId(int id) {

        /*
         * Получение данных об авторизованном пользователе
         */

        ApiUsers apiUsers = this.getApiUsers();

        Role role = apiUsers.getRole();

        Purchase purchase = purchaseRepository.getReferenceById(id);


        /*
         * Если админ, возвращает заказ по указанному ID
         */

        if (role == Role.ADMIN){

            return purchaseRepository.getReferenceById(id);

        /*
         * Если заказ принадлежит авторизованному пользователю - возвращат заказ по указанному ID
         */

        } else if (purchase.getClient().getId() == clientServiceFacade.findByPhone(apiUsers.getPhone()).getId()){

            return purchaseRepository.getReferenceById(id);


        /*
         * Если не выполнилось ни одно условие, бросаем исключение о недостаточных
         * правах на получение информации о заказе
         */

        } else {

            throw new RuntimeException("Не хватает прав для получения информации о заказе");

        }

    }


    /**
     * Данный метод проверяет:
     *      - права доступа к внесению изменений в заказ
     *      - сохраняет новые данные по заказу
     * @param id идентификатор заказа
     * @param goodsId Новый список товаров
     * @param servicesId Новый список услуг
     * @return Вернет обновенный заказ
     */
    public Purchase update(int id, int[] goodsId, int[] servicesId) {

        /*
         * Получение данных об авторизованном пользователе
         */

        ApiUsers apiUsers = this.getApiUsers();

        Role role = apiUsers.getRole();

        Purchase purchase = purchaseRepository.getReferenceById(id);


        /*
         * Если админ, обновляем заказ по указанному ID
         */

        if (role == Role.ADMIN){

            if (purchaseRepository.existsById(id)) {

                purchase.setGoodsList(this.addGoodsInPurchase(goodsId));
                purchase.setServicesList(this.addServicesInPurchase(servicesId));

                purchaseRepository.save(purchase);

                return purchase;

            } else {

                return null;

            }

        /*
         * Если заказ принадлежит авторизованному пользователю - обновляем заказ
         */

        } else if (purchase.getClient().getId() == clientServiceFacade.findByPhone(apiUsers.getPhone()).getId()){

            if (purchaseRepository.existsById(id)) {

                purchase.setGoodsList(this.addGoodsInPurchase(goodsId));
                purchase.setServicesList(this.addServicesInPurchase(servicesId));

                purchaseRepository.save(purchase);

                return purchase;

            } else {

                return null;

            }

        /*
         * Если не выполнилось ни одно условие, бросаем исключение о недостаточных
         * правах на обновление заказа
         */

        } else {

            throw new RuntimeException("Не хватает прав для обновления заказа");

        }



    }


    /**
     * Метод удаления заказа по указанному Id
     * @param id идентификатор заказа
     * @return Вернет результат true - если удаление прошло успешно, false - в противном случае
     */
    public boolean delete(Integer id) {
        if (purchaseRepository.existsById(id)) {
            purchaseRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Метод оплаты заказа
     * @param id идентификатор заказа
     * @param paymentMethod способ оплаты
     * @return Вернет true - в случае успешной оплаты, false - в противном случае
     */
    public boolean pay(int id, PaymentMethod paymentMethod){

        /*
         * Получение платежного сервиса для выбранного способа оплаты
         */

        IPaymentService paymentService = paymentServiceFactory.create(paymentMethod);

        /*
         * Получение данных об авторизованном пользователе
         */

        ApiUsers apiUsers = this.getApiUsers();

        Role role = apiUsers.getRole();

        Purchase purchase = purchaseRepository.getReferenceById(id);

        /*
         * Если админ, совершаем оплату по указанному заказу
         */

        if (role == Role.ADMIN){

            return this.payExecutor(purchase, paymentService);

        /*
         * Если заказ принадлежит авторизованному пользователю - совершаем оплату по заказу
         */

        } else if (purchase.getClient().getId() == clientServiceFacade.findByPhone(apiUsers.getPhone()).getId()){

            return this.payExecutor(purchase, paymentService);

        /*
         * Если не выполнилось ни одно условие, бросаем исключение о недостаточных
         * правах на совершение оплаты по заказу
         */

        } else {

            throw new RuntimeException("Не хватает прав для оплаты заказа");

        }

    }

    /**
     * Метод получения записи api_users авторизованного пользователя
     * @return вернет данные api_users авторизованного пользователя
     */
    private ApiUsers getApiUsers(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return apiUsersService.findApiUsersByPhone(authentication.getName());

    }


    /**
     * Приватный метод совершения оплаты
     * @param purchase Заказ
     * @param paymentService сервис оплаты
     * @return Возвращает true - в случае успешной оплаты, false - в противном случае
     */
    private boolean payExecutor(Purchase purchase, IPaymentService paymentService){

        /*
        * Проверяем, что заказ находится в статусе, допустимом для оплаты
        */

        if (purchase.getStatus().equals(OrderStatus.CREATED)){

            boolean paymentResult = paymentService.pay(purchase);

            if (paymentResult){

                /*
                 * При успешной оплате меняем статус заказа и сохраняем изменения
                 */

                purchase.setStatus(OrderStatus.PAID);
                purchaseRepository.save(purchase);

                return paymentResult;

            } else {

                throw new RuntimeException("Не удалось выполнить оплату");

            }

        } else {

            throw new RuntimeException("Статус заказа не подходит для оплаты");

        }

    }


    /**
     * Приватный метод формирования списка товаров
     * @param listId список id товаров, которые необходимо добавить в заказ
     * @return Вернет список товаров (объектов)
     */
    private List<Goods> addGoodsInPurchase(int[] listId){

        List<Goods> goodsList;

        if(listId.length != 0){

            goodsList = new ArrayList<>();

            for (int j : listId) {
                goodsList.add(goodsService.readId(j));
            }

            return goodsList;

        } else {

            return null;

        }

    }


    /**
     * Приватный метод формирования списка услуг
     * @param listId Список id услуг, которые необходимо добавить в заказ
     * @return Вернет список услуг (ообъектов)
     */
    private List<Services> addServicesInPurchase(int[] listId){

        List<Services> servicesList;

        if(listId != null){

            servicesList = new ArrayList<>();

            for (int j : listId) {
                servicesList.add(serviceForServices.readId(j));
            }

            return servicesList;

        } else {

            return null;

        }

    }


    /**
     * Приватный метод с логикой создания заказа
     * Поскольку в заказе услуги как могут быть, так и могут отсутствовать предусмотрено 2 конструктора
     * @param goodsId Список товаров - не может быть пустой
     * @param servicesId Список услуг - может быть пустой
     * @param phoneNumber Телефон пользователя, кому будет принадлежать заказ
     * @return Вернет заказ
     */
    private Purchase purchaseCreater(int[] goodsId, int[] servicesId, String phoneNumber){

        /*
         *   формируем список услуг
         */
        List<Services> servicesList = this.addServicesInPurchase(servicesId);

        /*
         * Формируем список товаров
         */
        List<Goods> goodsList = this.addGoodsInPurchase(goodsId);


        /*
         * При наличии или отсутствии услуг в сформированных списках вызывается нужный конструктор
         * и создается заказ для указанного пользователя
         */
        if (servicesList != null && goodsList != null){

            Purchase purchase = new Purchase(
                    goodsList,
                    servicesList,
                    clientServiceFacade.findByPhone(phoneNumber)
            );

            purchaseRepository.save(purchase);

            return purchase;

        } else if (goodsList != null){

            Purchase purchase = new Purchase(
                    goodsList,
                    clientServiceFacade.findByPhone(phoneNumber)
            );

            purchaseRepository.save(purchase);

            return purchase;

        } else {

            return null;

        }

    }

}
