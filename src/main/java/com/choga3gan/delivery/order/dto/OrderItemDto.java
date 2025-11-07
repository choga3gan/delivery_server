package com.choga3gan.delivery.order.dto;

import com.choga3gan.delivery.order.domain.Price;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItemDto(
        @Schema(description = "매장 Id", example = "550e8400-e29b-41d4-a716-446655440001") UUID productId,
        @Schema(description = "매장 Id", example = "컴퓨터") String productName,
        @Schema(description = "매장 Id", example = "550e8400-e29b-41d4-a716-446655440001") UUID storeId,
        @Schema(description = "매장 Id", example = "2") int quantity, // 수량
        @Schema(description = "매장 Id", example = "1340000") Price productPrice
) {}
