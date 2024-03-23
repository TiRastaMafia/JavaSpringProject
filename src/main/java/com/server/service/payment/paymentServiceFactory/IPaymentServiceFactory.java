package com.server.service.payment.paymentServiceFactory;

import com.server.entity.payment.PaymentMethod;
import com.server.service.payment.IPaymentService;

/**
 * Сервис фабрики
 */
public interface IPaymentServiceFactory {

    /**
     * Фабричный метод создания сервиса оплаты
     * @param paymentMethod Принимает способ оплаты
     * @return Вернет сервис оплаты, соответствубщий выбранному способу оплаты
     */
    IPaymentService create(PaymentMethod paymentMethod);

}
