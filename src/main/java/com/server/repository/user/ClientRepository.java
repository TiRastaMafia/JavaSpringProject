package com.server.repository.user;


import com.server.entity.user.ApiUsers;
import com.server.entity.user.Client;
import com.server.entity.user.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Репозиторий для работы с пользователями
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Метод для фильтрации пользователей по полу
     * @param gender пол
     * @return список пользователей указанного пола
     */
    List<Client> findClientByGender(Gender gender);

    /**
     * Метод поиска пользователя по его идентификационнам данным
     * @param apiUsers идентификационные данные пользователя
     * @return объект Пользователь с указанными данными
     */
    Client findClientByApiUsers(ApiUsers apiUsers);

}

