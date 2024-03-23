package com.server.service.user.userServiceFacade;

import com.server.dto.user.UserRegistrationDto;
import java.util.List;

/**
 * Обощенный интерфейс для работы с сервисами ползователей
 * @param <T> тип данных пользователя
 * @param <E> тип данных идентификацинных данных пользователя
 * @param <tId> тип данный идентификатора пользователя
 */
public interface IUserServiceFacade<T, E, tId>{

    /**
     * Метод для регистрации нового пользователя
     * @param userRegistrationDto идентификационные данные пользователя
     * @return Объект пользователя указанного типа
     */
    T create(UserRegistrationDto userRegistrationDto);

    /**
     * Метод для получения списка пользователей определенного типа
     * @return список пользователей указанного типа
     */
    List<T> readAll();

    /**
     * Метод для получения одного конкретного пользователя определенного типа
     * @param id идентификатор пользователя
     * @return объект пользователя указанного типа
     */
    T read(tId id);

    /**
     * Метод для обновления информации о пользователе
     * @param dto новая информация пользователя
     * @param id идентификатор обновляемого пользователя
     * @return объект пользователя указанного типа с обновленной информацией
     */
    T update(E dto, tId id);

    /**
     * Метод для удаления пользвателя
     * @param id идентификатор пользователя
     * @return true - при успешном выполнении операции, false - в противном случае
     */
    boolean delete(tId id);

}
