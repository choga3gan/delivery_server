/**
 * @package     com.choga3gan.delivery.store.exception
 * @class       StoreNotEditableException
 * @description 매장의 수정/삭제 권한이 없을 경우 발생하는 예외 정의
 *
 * @author      jinnk0
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.store.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StoreNotEditableException extends CustomException {
    public StoreNotEditableException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public StoreNotEditableException() {
        super(HttpStatus.UNAUTHORIZED, "매장의 수정/삭제 권한이 없습니다.");
    }
}
