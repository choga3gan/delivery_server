package com.choga3gan.delivery.global.utils.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }

    public BadRequestException() {
        this("잘못된 요청입니다.");
    }
}