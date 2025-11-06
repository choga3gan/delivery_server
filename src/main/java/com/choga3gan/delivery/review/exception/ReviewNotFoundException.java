package com.choga3gan.delivery.review.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends CustomException {
    public ReviewNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ReviewNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 리뷰가 삭제되었거나 존재하지 않습니다.");
    }
}
