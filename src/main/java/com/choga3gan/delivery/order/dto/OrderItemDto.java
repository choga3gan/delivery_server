package com.choga3gan.delivery.order.dto;

import com.choga3gan.delivery.order.domain.Price;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItemDto(
        UUID productId,
        String productName,
        UUID storeId,
        int quantity, // 수량
        Price productPrice
) {}
