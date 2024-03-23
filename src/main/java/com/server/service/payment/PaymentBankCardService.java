package com.server.service.payment;

import com.server.entity.payment.Payment;
import com.server.entity.payment.PaymentMethod;
import com.server.entity.purchase.Purchase;
import com.server.repository.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class PaymentBankCardService implements IPaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentBankCardService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean pay(Purchase purchase) {

        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.BANK_CARD);
        payment.setPurchase(purchase);
        payment.setPayAmount(purchase.getPurchaseAmount());

        paymentRepository.save(payment);

        return true;

    }
}
