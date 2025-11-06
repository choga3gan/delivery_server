package com.choga3gan.delivery.review.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ReviewNotEditableException extends CustomException {
    public ReviewNotEditableException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public ReviewNotEditableException() {
        super(HttpStatus.FORBIDDEN, "리뷰 수정 권한이 없습니다.");
    }
}
