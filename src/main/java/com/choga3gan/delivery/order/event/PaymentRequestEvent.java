package com.choga3gan.delivery.order.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentRequestEvent {
    private final UUID orderId;

    public PaymentRequestEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
