package com.choga3gan.delivery.global.utils.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException() {
        this("자원을 찾을 수 없습니다.");
    }
}

