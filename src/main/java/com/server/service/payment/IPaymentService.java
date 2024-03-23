package com.server.service.payment;

import com.server.entity.purchase.Purchase;

/**
 * Сервис оплаты
 */
public interface IPaymentService {

    /**
     * Метод оплаты заказа
     * @param purchase - Метод принимает заказ, который необзодимо оплатить
     * @return true - если оплата прошла успешно, false - если опалата не прошла
     */
    boolean pay(Purchase purchase);

}
