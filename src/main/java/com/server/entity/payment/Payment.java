package com.server.entity.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.server.entity.purchase.Purchase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Платеж")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Идентификатор платежа")
    int id;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    @Schema(description = "Идентификатор заказа")
    Purchase purchase;

    @Column(nullable = false)
    @Schema(description = "Сумма оплаты")
    int payAmount;

    @Column(nullable = false, name = "create_time")
    @CreationTimestamp
    @Schema(description = "Дата и время платежа")
    LocalDateTime dateTime;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @Schema(description = "Способ оплаты")
    PaymentMethod paymentMethod;

}
