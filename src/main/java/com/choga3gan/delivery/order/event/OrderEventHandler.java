package com.choga3gan.delivery.order.event;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.order.service.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    @Async
    @EventListener
    public void handleOrderAcceptEvent(OrderAcceptEvent orderAcceptEvent) {
        Order order = orderRepository.findByOrderId(orderAcceptEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        publisher.publishEvent(PaymentRequestEvent.class);
        orderRepository.save(order);
        log.trace("Order has been accepted");
    }

    @Async
    @EventListener
    public void handleOrderRefundEvent(OrderRefundEvent orderRefundEvent) {
        Order order = orderRepository.findByOrderId(orderRefundEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        publisher.publishEvent(PaymentCancelEvent.class);
        orderRepository.save(order);
        log.trace("Order has been cancelled");
    }
}
