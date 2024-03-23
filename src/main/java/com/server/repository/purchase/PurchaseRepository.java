package com.server.repository.purchase;


import com.server.entity.purchase.OrderStatus;
import com.server.entity.purchase.Purchase;
import com.server.entity.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с заказами
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    /**
     * Метод для поиска всех заказов указанного пользователя
     * @param client пользователь
     * @return список заказов указанного пользователя
     */
    List<Purchase> findAllByClient(Client client);

    /**
     * Метод для фильтрации заказов по статусу
     * @param orderStatus статус заказа
     * @return список заказов в указанном статусе
     */
    List<Purchase> findAllByStatus(OrderStatus orderStatus);

}
