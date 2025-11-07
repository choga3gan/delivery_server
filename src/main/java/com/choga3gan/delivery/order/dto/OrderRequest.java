package com.choga3gan.delivery.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(
        @NotBlank @Schema(description = "매장 Id", example = "냉장고") String name,
        @NotBlank @Schema(description = "매장 Id", example = "testUser002@test.org") String email,
        @NotBlank @Schema(description = "매장 Id", example = "경기") String address,
        List<OrderItemRequest> items
) {}
