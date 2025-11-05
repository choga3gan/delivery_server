package com.choga3gan.delivery.order.domain;

// RuntimeException을 상속하여 언체크 예외로 처리
public class OrderCancelException extends RuntimeException {

    public OrderCancelException(String message) {
        super(message);
    }
}
