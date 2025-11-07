package com.choga3gan.delivery.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record OrderResponse(
        @Schema(description = "매장 Id", example = "550e8400-e29b-41d4-a716-446655440003") UUID orderId
) {}
