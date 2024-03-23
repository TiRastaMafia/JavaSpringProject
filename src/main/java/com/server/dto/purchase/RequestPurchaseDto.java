package com.server.dto.purchase;

import com.server.entity.product.Goods;
import com.server.entity.product.Services;
import com.server.entity.purchase.OrderStatus;
import java.util.List;

public record RequestPurchaseDto(
        int id,
        List<Goods> goodsList,
        List<Services> servicesList,
        int purchaseAmount,
        OrderStatus status,
        int clientId
        ) {
}
