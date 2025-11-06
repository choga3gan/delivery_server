/**
 * @package     com.choga3gan.delivery.review.exception
 * @class       ReviewNotEditableException
 * @description 리뷰 수정 권한이 없을 때 (본인이 작성한 리뷰가 아닐 때) 발생하는 예외 정의
 *
 * @author      jinnk0
 * @since       2025. 11. 6.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 6.        jinnk0       최초 생성
 * </pre>
 */

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
