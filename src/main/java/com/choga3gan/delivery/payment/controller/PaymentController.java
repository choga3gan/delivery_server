package com.choga3gan.delivery.payment.controller;

import com.choga3gan.delivery.payment.domain.Payment;
import com.choga3gan.delivery.payment.dto.PaymentResponse;
import com.choga3gan.delivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/v1/users/payments")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsFromUser() {
        Page<Payment> paymentPage = paymentService.getPaymentFromUser();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentPage.map(PaymentResponse::from));
    }

    @GetMapping("/v1/stores/{storeId}/payments")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsFromStore(@PathVariable UUID storeId) {
        Page<Payment> paymentPage = paymentService.getPaymentFromStore(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentPage.map(PaymentResponse::from));
    }
}
