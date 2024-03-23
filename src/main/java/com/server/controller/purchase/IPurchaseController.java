package com.server.controller.purchase;

import com.server.dto.purchase.RequestPurchaseDto;
import com.server.entity.payment.PaymentMethod;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("v1/purchase")
@Tag(
        name = "Заказы",
        description = "Все методы для работы с заказами"
)
public interface IPurchaseController {

    /**
     * Метод создания нового заказа
     * @param goodsId список товаров
     * @param servicesId список услуг
     * @return информация о созданном заказе
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    ResponseEntity<RequestPurchaseDto> create(
            @RequestParam("goods")int[] goodsId,
            @RequestParam(value = "services", required = false)int[] servicesId);



    /**
     * Метод для ручного создания заказа для указанного клиента
     * @param goodsId список товаров
     * @param servicesId список услуг
     * @param phone телефон клиента, совершающего заказ
     * @return информация о созданном заказе
     */
    @RequestMapping(value = "/manual-create", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<RequestPurchaseDto> manualCreate(
            @RequestParam("goods")int[] goodsId,
            @RequestParam("services")int[] servicesId,
            @RequestParam("phone")String phone);



    /**
     * Метод для получения списка всех заказов
     * @return Все заказы - для пользователя с роль Админ,
     *          Все заказы пользователя для пользователя с ролью Клиент
     */
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    ResponseEntity<List<RequestPurchaseDto>> readAll();



    /**
     * Метод для получения информации по одному конкретному заказу
     * @param id идентификатор заказа
     * @return информация по указанному заказу
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    ResponseEntity<RequestPurchaseDto> readId(@PathVariable(name = "id") int id);



    /**
     * Метод для обновления заказа
     * @param id идентификатор обновляемого заказа
     * @param goodsId новый список товаров
     * @param servicesId новый список услуг
     * @return информация по обновленному заказу
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    ResponseEntity<RequestPurchaseDto> update(
            @PathVariable(name = "id") int id,
            @RequestParam("goods")int[] goodsId,
            @RequestParam("services")int[] servicesId);



    /**
     * Метод для удаления заказа
     * @param id идентификатор заказа
     * @return статус код, соответствующий результату выполнения операции
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    ResponseEntity<?> delete(@PathVariable(name = "id") int id);



    /**
     * Метод для оплаты заказа
     * @param id идентификатор заказа
     * @param paymentMethod способ оплаты
     * @return статус код, соответствующий результату выполнения операции
     */
    @RequestMapping(value = "/{id}/pay", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    ResponseEntity<?> pay(
            @PathVariable(name = "id") int id,
            @RequestParam(name = "Способ оплаты") PaymentMethod paymentMethod
    );

}
