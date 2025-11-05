package com.choga3gan.delivery.order.dto;

import com.choga3gan.delivery.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateStatusRequestDto {
    private OrderStatus status;
}
