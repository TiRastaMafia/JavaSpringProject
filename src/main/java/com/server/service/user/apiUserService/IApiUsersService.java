package com.server.service.user.apiUserService;

import com.server.entity.user.ApiUsers;
import java.util.List;

/**
 * Обобщенный интерфейс для работы с чувствительными данными пользователей
 * @param <T> тип данных объекта
 * @param <tId> - тип данных идентификатора
 * @param <tPhone> тип данных номера телефона
 */
public interface IApiUsersService<T, tId, tPhone> {

    /**
     * Метод для создания
     * @param t объект
     */
    void create(T t);

    /**
     * Метод для получения всего списка объектов
     * @return объект указанного типа данных
     */
    List<T> readAll();


    /**
     * Метод получения конкретного объекта
     * @param id идентификатор объекта
     * @return объект указанного ипа данных
     */
    T readId(tId id);


    /**
     * Метод для обновления информации по объекту
     * @param t объект с обновленными характеристиками
     * @param id идентификатор объекта
     * @return true - при успеном обновлении, false - в противном случае
     */
    boolean update(T t, tId id);


    /**
     * Метод для удаления объекта
     * @param id идентификатор объекта
     * @return true - при успеном обновлении, false - в противном случае
     */
    boolean delete(tId id);
    ApiUsers findApiUsersByPhone(tPhone phone);
}
