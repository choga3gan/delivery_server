package com.choga3gan.delivery.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record OrderUpdateRequest(
        @NotBlank @Schema(description = "매장 Id", example = "선풍기") String name,
        @NotBlank @Schema(description = "매장 Id", example = "testUser003@test.org") String email,
        @NotBlank @Schema(description = "매장 Id", example = "강원") String address
) {}
