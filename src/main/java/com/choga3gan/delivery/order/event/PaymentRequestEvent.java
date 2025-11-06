package com.choga3gan.delivery.order.event;

import com.choga3gan.delivery.order.domain.Order;
import lombok.Getter;

@Getter
public class PaymentRequestEvent {
    private Order order;

    public PaymentRequestEvent(Order order) {
        this.order = order;
    }
}
