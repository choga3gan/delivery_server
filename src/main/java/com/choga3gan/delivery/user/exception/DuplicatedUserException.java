package com.choga3gan.delivery.user.exception;

import com.choga3gan.delivery.global.utils.exception.BadRequestException;

public class DuplicatedUserException extends BadRequestException {
    public DuplicatedUserException() {
        super("이미 가입된 회원입니다.");
    }
}
