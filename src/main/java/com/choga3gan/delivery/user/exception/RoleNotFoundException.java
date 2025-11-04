package com.choga3gan.delivery.user.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends CustomException {
    public RoleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
public RoleNotFoundException() {
    super(HttpStatus.NOT_FOUND, "존재하지 않는 역할입니다.");
}
}
