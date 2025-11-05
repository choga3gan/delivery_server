package com.choga3gan.delivery.user.exception;

import com.choga3gan.delivery.global.utils.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }
}
