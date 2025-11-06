package com.choga3gan.delivery.order.domain;

import com.choga3gan.delivery.global.utils.exception.BadRequestException;

// RuntimeException을 상속하여 언체크 예외로 처리
public class OrderItemNotExistException extends BadRequestException {

    public OrderItemNotExistException() {
        super("주문에는 최소 1개 이상의 상품이 포함되어야 합니다.");
    }
}
