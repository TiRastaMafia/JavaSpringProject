package com.server.service.user.userServiceFacade;

import com.server.dto.user.ClientDto;
import com.server.dto.user.RequestClientDto;
import com.server.dto.user.UserRegistrationDto;
import com.server.entity.user.ApiUsers;
import com.server.entity.user.Client;
import com.server.entity.user.Gender;
import com.server.entity.user.Role;
import com.server.service.user.apiUserService.ApiUsersService;
import com.server.service.user.clientsService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для синхронизации работы всех необходимых сервисов для работы с пользователем
 */
@Service
public class ClientServiceFacade implements IUserServiceFacade<Client, ClientDto, Integer> {

    private final ApiUsersService apiUsersService;

    private final ClientService clientService;


    @Autowired
    public ClientServiceFacade(ApiUsersService apiUsersService, ClientService clientService) {
        this.apiUsersService = apiUsersService;
        this.clientService = clientService;
    }


    @Override
    public Client create(UserRegistrationDto userRegistrationDto) {

        ApiUsers apiUser = new ApiUsers();
        apiUser.setPhone(userRegistrationDto.phone());
        apiUser.setRole(Role.LEAD);
        apiUser.setPassword(userRegistrationDto.password());

        apiUsersService.create(apiUser);

        Client client = new Client();
        client.setApiUsers(apiUser);
        client.setName(userRegistrationDto.name());

        clientService.create(client);

        return client;

    }

    @Override
    public List<Client> readAll() {

        return clientService.readAll();

    }

    @Override
    public Client read(Integer id) {

        return clientService.readId(id);

    }


    /** Данный метод проверяет:
        - права доступа к внесению изменений
        - сохраняет новые данные
        - при необходимости и определенных условиях меняет роль пользователя (приватный метод updateRole)
     */
    @Override
    public Client update(ClientDto clientDto, Integer id) {

        // Получаем все данные по авторизованному пользователю

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phone = authentication.getName();

        ApiUsers apiUsers = apiUsersService.findApiUsersByPhone(phone);

        Role role = apiUsers.getRole();


        // Если роль авторизованного пользователя - Админ, то применяем изменения

        if (role == Role.ADMIN){

            Client client = clientService.update(clientDto, id);

            this.updateRole(client, apiUsers);

            return client;

        // Если роль авторизованного пользователья не Админ, то проверяем, что ID авторизованного
        // пользователя совпадает с ID пользователя, по которому мы хотим обновить информацию

        } else if (this.findByPhone(phone).getId() == id){

            Client client = clientService.update(clientDto, id);

            this.updateRole(client, client.getApiUsers());

            return client;

        // Если не сработало ни одно условие, то бросаем исключение об отсутствии прав на изменения

        } else {

            throw new RuntimeException("Нет доступа для изменений!");

        }

    }

    @Override
    public boolean delete(Integer id) {

        return clientService.delete(id);

    }

    /**
     * Метод для фильтрации пользователей по полу
     * @param gender пол для фильтра
     * @return список пользователей указанного пола
     */
    public List<Client> filterByGender(Gender gender){

        return clientService.filterByGender(gender);

    }

    /**
     * Метод поиска клиента по номеру телефона
     * @param phone номер телефона
     * @return обект Пользователь с указанным номером телефона
     */
    public Client findByPhone(String phone) {

        return clientService.findClientByApiUsers(apiUsersService.findApiUsersByPhone(phone));

    }


    /**
     *  Приватный метод обновляет роль пользователя если текщая роль - Лид
     *  и переданы все неообходимые данные пользователя для получения роль - Клиент
     */
    private void updateRole(Client client, ApiUsers apiUsers){

        if (apiUsers.getRole() == Role.LEAD){

            if (!client.getName().isBlank() && !client.getEmail().isBlank()){

                apiUsers.setRole(Role.CLIENT);

                apiUsersService.update(apiUsers, apiUsers.getId());

            }

        }

    }


    /**
     * Публичный метод для сериализации данных одного объекта,
     * полученного из базы данных в Dto для передачи на контроллер
     */
    public RequestClientDto serialisInDtoObject(Client client){

        return new RequestClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getApiUsers().getPhone(),
                client.getGender()
        );

    }


    /**
     * Публичный метод для сериализации данных списка объектов,
     * полученных из базы данных в Dto для передачи на контроллер
     */
    public List<RequestClientDto> serialisInListDtoObject(List<Client> clientList){

        List<RequestClientDto> requestClientDtoList = new ArrayList<>();

        for(Client client : clientList){

            requestClientDtoList.add(new RequestClientDto(
                            client.getId(),
                            client.getName(),
                            client.getEmail(),
                            client.getApiUsers().getPhone(),
                            client.getGender())
            );
        }

        return requestClientDtoList;

    }

}
