package com.choga3gan.delivery.order.event;

import com.choga3gan.delivery.global.event.Events;
import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.order.service.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository orderRepository;

    @Async
    @EventListener(OrderAcceptEvent.class)
    public void handleOrderAcceptEvent(OrderAcceptEvent orderAcceptEvent) {
        Order order = orderRepository.findByOrderId(orderAcceptEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);

        orderRepository.saveAndFlush(order);
        log.info("Order has been accepted");
        Events.trigger(new PaymentRequestEvent(order.getOrderId()));
    }

    @Async
    @EventListener(OrderRefundEvent.class)
    public void handleOrderRefundEvent(OrderRefundEvent orderRefundEvent) {
        Order order = orderRepository.findByOrderId(orderRefundEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        orderRepository.saveAndFlush(order);
        log.info("Order has been cancelled");
        Events.trigger(new PaymentCancelEvent(order.getOrderId()));
    }
}
