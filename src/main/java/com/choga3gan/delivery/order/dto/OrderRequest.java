package com.choga3gan.delivery.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String address,
        List<OrderItemRequest> items
) {}
