package com.choga3gan.delivery.order.domain;

import com.choga3gan.delivery.global.utils.exception.BadRequestException;

// RuntimeException을 상속하여 언체크 예외로 처리
public class OrderCancelException extends BadRequestException {

    public OrderCancelException() {
        super("주문 취소에 실패하였습니다.");
    }
}
