package com.choga3gan.delivery.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record OrderUpdateRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String address
) {}
