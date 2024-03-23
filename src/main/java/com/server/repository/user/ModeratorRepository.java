package com.server.repository.user;

import com.server.entity.user.ApiUsers;
import com.server.entity.user.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с администраторами
 */
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    /**
     * Метод поиска администратора по идентификационным данным
     * @param apiUsers идентификационные данные администратора
     * @return объект Moderator с указанными данными
     */
    Moderator findModeratorByApiUsers(ApiUsers apiUsers);

}
