package com.choga3gan.delivery.payment.service;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.payment.domain.Payment;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PaymentService {
    Payment createPayment(Order order);
    Page<Payment> getPaymentFromUser();
    Page<Payment> getPaymentFromStore(UUID storeId);
    void cancelPayment(UUID paymentId);
}
