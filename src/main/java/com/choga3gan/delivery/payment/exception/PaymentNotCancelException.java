package com.choga3gan.delivery.payment.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PaymentNotCancelException extends CustomException {
    public PaymentNotCancelException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public PaymentNotCancelException() {
        super(HttpStatus.FORBIDDEN, "결제 취소 권한이 없습니다.");
    }
}
