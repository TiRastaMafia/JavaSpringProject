package com.server.service.user.clientsService;

import com.server.annotation.LogException;
import com.server.annotation.LogExecution;
import com.server.dto.user.ClientDto;
import com.server.entity.user.ApiUsers;
import com.server.entity.user.Client;
import com.server.entity.user.Gender;
import com.server.repository.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Сервис для работы с пользователями
 */
@Service
public class ClientService{

    @Autowired
    private ClientRepository clientRepository;


    /**
     * Метод для создания пользователя
     * @param client данные пользователя
     */
    @LogExecution
    @LogException
    public void create(Client client) {

        clientRepository.save(client);

    }

    /**
     * Метод получения списка всех пользователей
     * @return список объектов ПОльзователь
     */
    @LogExecution
    @LogException
    public List<Client> readAll() {

        return clientRepository.findAll();

    }

    /**
     * Метод для получения одного конкретного пользователя
     * @param id идентификатор пользователя
     * @return обект Пользовтель
     */
    @LogExecution
    @LogException
    public Client readId(Integer id) {

        return clientRepository.getReferenceById(id);

    }

    /**
     * Метод для обновления данных пользователя
     * @param clientDto Обновленные данные пользователя
     * @param id Идентификатор обновляемого пользователя
     * @return объект Пользователь с обновленными данными
     */
    @LogExecution
    @LogException
    public Client update(ClientDto clientDto, Integer id) {

        // Если пользователь с таким идентификатором найден в базе - выполняем обновление данных

        if (clientRepository.existsById(id)) {

            Client client = clientRepository.getReferenceById(id);

            // Если не передано новое имя пользователя - не обновляем имя

            if (!clientDto.name().isBlank()){

                client.setName(clientDto.name());

            }

            // Если не передана новая почта пользователя - не обновляем почту

            if (!clientDto.email().isBlank()){

                client.setEmail(clientDto.email());

            }

            client.setGender(clientDto.gender());

            clientRepository.save(client);

            return client;

        } else {

            return null;

        }

    }

    /**
     * Метод для удаления пользвоаетля
     * @param id идентификатор пользователя
     * @return true - при успешном удалении, false - в противном случае
     */
    @LogExecution
    @LogException
    public boolean delete(Integer id) {

        if (clientRepository.existsById(id)) {

            clientRepository.deleteById(id);

            return true;

        } else {

            throw new RuntimeException("Клиента с " + id + " не найдено");

        }
    }

    /**
     * Метод для фильтрации пользователей по полу
     * @param gender пол для фильтра
     * @return списк пользователей с указанным полом
     */
    @LogExecution
    @LogException
    public List<Client> filterByGender(Gender gender){

        return clientRepository.findClientByGender(gender);

    }

    /**
     * Метод для поиска клиента по его данным из api_users
     * @param apiUsers объект api_users пользователя
     * @return обеъект Пользователь
     */
    public Client findClientByApiUsers(ApiUsers apiUsers) {

        Client client = clientRepository.findClientByApiUsers(apiUsers);

        if (client != null){

            return client;

        } else {

            return null;

        }
    }
}