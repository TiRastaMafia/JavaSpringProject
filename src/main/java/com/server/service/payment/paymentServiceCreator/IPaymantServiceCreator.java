package com.server.service.payment.paymentServiceCreator;

import com.server.service.payment.IPaymentService;

/**
 * Создатель сервиса оплаты
 */
public interface IPaymantServiceCreator {

    /**
     * Метод для создания сервиса оплаты
     * @return Вернет объект сервиса оплаты, который имплиментирует интерфейс IPaymentService
     */
    IPaymentService create();

}
