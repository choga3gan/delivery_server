/**
 * @package     com.choga3gan.delivery.store.exception
 * @class       StoreNotFoundException
 * @description 매장 조회 시 발생할 수 있는 예외 정의
 *
 * @author      jinnk0
 * @since       2025. 11. 5.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 5.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.store.exception;

import com.choga3gan.delivery.global.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StoreNotFoundException extends CustomException {
    public StoreNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public StoreNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 매장이 삭제되었거나 존재하지 않습니다.");
    }
}
