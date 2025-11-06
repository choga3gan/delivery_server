package com.choga3gan.delivery.order.dto;

import com.choga3gan.delivery.order.domain.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderDto(
        UUID orderId,
        OrderStatus orderStatus,
        UUID userId,
        String userName,
        String userEmail,
        String userAddress,
        boolean reviewed,
        LocalDateTime createdAt,
        List<OrderItemDto> items
) {}
