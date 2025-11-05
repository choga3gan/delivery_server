package com.choga3gan.delivery.order.domain;

// RuntimeException을 상속하여 언체크 예외로 처리
public class OrderItemNotExistException extends RuntimeException {

    public OrderItemNotExistException() {
        super("주문에는 최소 1개 이상의 상품이 포함되어야 합니다.");
    }
}
