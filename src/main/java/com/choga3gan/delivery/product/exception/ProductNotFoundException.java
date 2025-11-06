package com.choga3gan.delivery.product.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 상품이 삭제되었거나 존재하지 않습니다.");
    }
}
