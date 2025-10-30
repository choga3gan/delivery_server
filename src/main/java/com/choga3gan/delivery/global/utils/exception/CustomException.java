/**
 * @package     com.choga3gan.delivery.user.global.utils.exception
 * @class       CustomException
 * @description 직접 예외를 커스텀하여 처리할 경우 상속받아 사용할 수 있는 클래스 규격 지정
 *
 * @author      허진경
 * @since       2025.10.30
 * @version     1.0.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025.10.30    허진경          최초 생성
 * </pre>
 */

package com.choga3gan.delivery.global.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatusCode status;

    public CustomException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }
}
