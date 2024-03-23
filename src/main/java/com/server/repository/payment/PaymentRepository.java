package com.server.repository.payment;

import com.server.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с платежами
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
