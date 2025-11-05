package com.choga3gan.delivery.user.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicatedRoleException extends CustomException {
    public DuplicatedRoleException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    public DuplicatedRoleException() {super(HttpStatus.BAD_REQUEST, "중복된 역할 이름이 존재합니다.");}
}
