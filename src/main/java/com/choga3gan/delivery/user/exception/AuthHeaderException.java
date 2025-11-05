package com.choga3gan.delivery.user.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AuthHeaderException extends CustomException {
    public AuthHeaderException(String message) {

        super(HttpStatus.UNAUTHORIZED, message);
    }
    public AuthHeaderException() {this("잘못된 인증헤더 입니다.");}
}
