package com.server.service.payment.paymentServiceCreator;

import com.server.repository.payment.PaymentRepository;
import com.server.service.payment.IPaymentService;
import com.server.service.payment.PaymentBankCardService;
import com.server.service.payment.paymentServiceCreator.IPaymantServiceCreator;


public class PaymentBankCardServiceCreator implements IPaymantServiceCreator {

    private final PaymentRepository paymentRepository;

    public PaymentBankCardServiceCreator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public IPaymentService create() {

        return new PaymentBankCardService(paymentRepository);

    }
}
