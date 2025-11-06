package com.choga3gan.delivery.payment.dto;

import com.choga3gan.delivery.order.domain.Price;
import com.choga3gan.delivery.payment.domain.Payment;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.dto.UserResponse;

import java.util.UUID;

public record PaymentResponse(
        Price totalPrice,
        int quantity,
        UUID userId,
        UUID paymentId,
        UUID storeId
) {
    public static PaymentResponse from(Payment payment){
        return new PaymentResponse(
                payment.getTotalPrice(),
                payment.getQuantity(),
                payment.getUserId(),
                payment.getPaymentId(),
                payment.getStoreId()
        );
    }
}
