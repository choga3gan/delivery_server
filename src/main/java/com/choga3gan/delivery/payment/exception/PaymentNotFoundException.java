package com.choga3gan.delivery.payment.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PaymentNotFoundException extends CustomException {
    public PaymentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public PaymentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "결제 건을 찾을 수 없습니다.");
    }
}
