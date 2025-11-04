/**
 * @package     com.choga3gan.delivery.category.exception
 * @class       DuplicateCategoryNameException
 * @description 새로 추가하려고 하는 카테고리 이름이 이미 존재할 경우 발생하는 예외 정의
 *
 * @author      jinnk0
 * @since       2025. 11. 3.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 3.        jinnk0       최초 생성
 * </pre>
 */
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
