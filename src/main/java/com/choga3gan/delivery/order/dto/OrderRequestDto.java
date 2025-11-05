package com.choga3gan.delivery.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {
    private UUID userId;
    private UUID storeId;
    private String userAddress; // 사용자 주소 추가
    private List<OrderItemDto> orderItems;
}

/*import com.choga3gan.delivery.order.domain.OrderStatus;
import com.choga3gan.delivery.order.domain.Price;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderRequestDto {
    private UUID userId; // 누가 주문했는지

    private Double quantity;

    @NotNull(message = "총 결제 금액은 필수입니다.")
    @Min(value = 1, message = "결제 금액은 0보다 커야 합니다.")
    private Price totalPrice;


    private OrderStatus orderStatus;
    private UUID usedId;
    private String userAddress;
    private UUID storeId;
    private boolean reviewed;

    // 주문 상품 목록 정보(List<OrderItemDto)가 포함되어야 하지만,
    // 아직은 간단한 CRUD를 위해 생략
}*/
