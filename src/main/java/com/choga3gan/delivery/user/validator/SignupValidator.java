package com.choga3gan.delivery.user.validator;

import com.choga3gan.delivery.global.utils.exception.BadRequestException;
import com.choga3gan.delivery.user.dto.SignupRequest;
import com.choga3gan.delivery.user.exception.DuplicatedUserException;
import com.choga3gan.delivery.user.repository.UserRepository;

/**
 *
 * 회원가입 추가 검증
 *
 */
public class SignupValidator {

    public void validate(SignupRequest req, UserRepository repository) {
        /**
         *
         * 1. 비밀번호 복잡성
         * 2. 비밀번호 + 비밀번호 확인
         * 3. 중복 회원 여부
         * 4. 회원 약관 동의
         * 5. ...
         */
        if (!req.password().equals(req.confirmPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 중복 회원 여부
        if (repository.existsByUsername(req.username())) {
            throw new DuplicatedUserException();
        }
    }
}
