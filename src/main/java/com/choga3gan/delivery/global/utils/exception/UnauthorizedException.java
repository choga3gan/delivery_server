package com.choga3gan.delivery.global.utils.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException{

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException() {
        this("접근 권한이 없습니다.");
    }
}
