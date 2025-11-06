package com.choga3gan.delivery.order.dto;

import java.util.UUID;

public record OrderItemRequest(
        UUID productId,
        int quantity
) {}
