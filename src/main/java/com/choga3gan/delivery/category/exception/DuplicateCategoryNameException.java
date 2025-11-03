package com.choga3gan.delivery.category.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicateCategoryNameException extends CustomException {
    public DuplicateCategoryNameException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public DuplicateCategoryNameException() {
        super(HttpStatus.BAD_REQUEST, "카테고리 이름이 이미 존재합니다.");
    }
}
