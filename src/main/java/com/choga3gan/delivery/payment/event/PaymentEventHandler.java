package com.choga3gan.delivery.payment.event;

import com.choga3gan.delivery.order.event.PaymentCancelEvent;
import com.choga3gan.delivery.order.event.PaymentRequestEvent;
import com.choga3gan.delivery.payment.domain.Payment;
import com.choga3gan.delivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final PaymentService paymentService;

    @Async
    @EventListener
    public void handlePaymentRequestEvent(PaymentRequestEvent paymentRequestEvent) {
        paymentService.createPayment(paymentRequestEvent.getOrder());
    }

    @Async
    @EventListener
    public void handlePaymentCancelEvent(PaymentCancelEvent paymentCancelEvent) {
        paymentService.cancelPayment(paymentCancelEvent.getOrderId());
    }
}
