package com.server.entity.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Услуга")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Идентификатор услуги")
    @Setter
    private int id;

    @Column(nullable = false)
    @Schema(description = "Наименование услуги")
    @Setter
    private String productName;

    @Column(nullable = false)
    @Schema(description = "Стоимость услуги")
    private int amount;

    public void setAmount(int amount) {

        if (amount > 0){

            this.amount = amount;

        } else {

            throw new RuntimeException("Стоиимость должна быть больше нуля");

        }
    }
}
