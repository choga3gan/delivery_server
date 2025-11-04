/**
 * @package     com.choga3gan.delivery.category.exception
 * @class       CategoryNotFoundException
 * @description 삭제되었거나 존재하지 않는 카테고리를 조회하려고 할 경우 발생하는 예외 정의
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

public class CategoryNotFoundException extends CustomException {

    public CategoryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public CategoryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "삭제되었거나 존재하지 않는 카테고리입니다.");
    }
}
