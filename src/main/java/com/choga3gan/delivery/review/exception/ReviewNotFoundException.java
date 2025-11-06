/**
 * @package     com.choga3gan.delivery.review.exception
 * @class       ReviewNotFoundException
 * @description 해당하는 리뷰를 찾을 수 없을 때 발생하는 예외 처리
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

public class ReviewNotFoundException extends CustomException {
    public ReviewNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ReviewNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 리뷰가 삭제되었거나 존재하지 않습니다.");
    }
}
