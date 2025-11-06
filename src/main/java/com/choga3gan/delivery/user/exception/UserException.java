package com.choga3gan.delivery.user.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserException extends CustomException {
    public UserException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    public UserException() {this("잘못된 요청입니다.");}
}
