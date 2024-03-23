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
@Schema(description = "Товар")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Идентификатор товара")
    @Setter
    private int id;

    @Column(nullable = false)
    @Schema(description = "Наименование товара")
    @Setter
    private String productName;

    @Column(nullable = false)
    @Schema(description = "Категория товара")
    @Enumerated(EnumType.STRING)
    @Setter
    private CategoryGoods category;

    @Column(nullable = false)
    @Schema(description = "Стоимость товара")
    private int amount;


    public void setAmount(int amount) {
        if (amount > 0){

            this.amount = amount;

        } else {

            throw new RuntimeException("Стоиимость должна быть больше нуля");

        }

    }
}
