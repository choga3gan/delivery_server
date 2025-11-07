package com.choga3gan.delivery.order.dto;

import com.choga3gan.delivery.order.domain.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderDto(
        @NotBlank @Schema(description = "매장 Id", example = "550e8400-e29b-41d4-a716-446655440000") UUID orderId,
        @Schema(description = "매장 Id", example = "ORDER_PENDING") OrderStatus orderStatus,
        @Schema(description = "매장 Id", example = "550e8400-e29b-41d4-a716-446655440000") UUID userId,
        @Schema(description = "매장 Id", example = "testUser001") String userName,
        @Schema(description = "매장 Id", example = "testUser001@test.org")  String userEmail,
        @Schema(description = "매장 Id", example = "서울") String userAddress,
        @Schema(description = "매장 Id") boolean reviewed,
        @Schema(description = "매장 Id") LocalDateTime createdAt,
        @Schema(description = "주문 상품 리스트") List<OrderItemDto> items
) {}
