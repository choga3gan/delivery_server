package com.choga3gan.delivery.payment.event;

import com.choga3gan.delivery.order.event.PaymentCancelEvent;
import com.choga3gan.delivery.order.event.PaymentRequestEvent;
import com.choga3gan.delivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final PaymentService paymentService;

    @Async
    @EventListener(PaymentRequestEvent.class)
    @Transactional
    public void handlePaymentRequestEvent(PaymentRequestEvent paymentRequestEvent) {
        paymentService.createPayment(paymentRequestEvent.getOrderId());
        log.info("Payment has been created");
    }

    @Async
    @EventListener(PaymentCancelEvent.class)
    public void handlePaymentCancelEvent(PaymentCancelEvent paymentCancelEvent) {
        paymentService.cancelPayment(paymentCancelEvent.getOrderId());
        log.info("Payment has been cancelled");
    }
}
