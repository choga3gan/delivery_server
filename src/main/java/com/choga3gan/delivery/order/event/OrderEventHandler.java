package com.choga3gan.delivery.order.event;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.order.service.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository orderRepository;

    @Async
    @EventListener
    public void handleOrderAcceptEvent(OrderAcceptEvent orderAcceptEvent) {
        Order order = orderRepository.findByOrderId(orderAcceptEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        order.orderAccept();
        orderRepository.save(order);
    }

    @Async
    @EventListener
    public void handleOrderRefundEvent(OrderRefundEvent orderRefundEvent) {
        Order order = orderRepository.findByOrderId(orderRefundEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        order.cancel();
        orderRepository.save(order);
    }
}
