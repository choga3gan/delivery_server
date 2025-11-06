package com.choga3gan.delivery.order.event;

import com.choga3gan.delivery.order.domain.Order;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentCancelEvent {
    private UUID orderId;

    public PaymentCancelEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
