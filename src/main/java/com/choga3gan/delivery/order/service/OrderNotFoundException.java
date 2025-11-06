package com.choga3gan.delivery.order.service;

import com.choga3gan.delivery.global.utils.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException() {
        super("주문을 찾을 수 없습니다.");
    }
}
