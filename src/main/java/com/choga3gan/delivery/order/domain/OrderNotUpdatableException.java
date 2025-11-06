package com.choga3gan.delivery.order.domain;

import com.choga3gan.delivery.global.utils.exception.BadRequestException;

public class OrderNotUpdatableException extends BadRequestException {
    public OrderNotUpdatableException(String message) {
        super(message);
    }
}
