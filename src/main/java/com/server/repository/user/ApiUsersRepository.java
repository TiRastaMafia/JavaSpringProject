package com.server.repository.user;

import com.server.entity.user.ApiUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Репозиторий для работы с идентификационными данными пользователя
 */
public interface ApiUsersRepository  extends JpaRepository<ApiUsers, Integer> {

    /**
     * Метод поиска записи по номеру телефона
     * @param phone ноер телефона
     * @return запись api_users с указанными номером
     */
    Optional<ApiUsers> findApiUsersByPhone(String phone);

}
