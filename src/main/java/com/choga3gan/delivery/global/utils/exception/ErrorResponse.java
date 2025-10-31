/**
 * @package     com.choga3gan.delivery.user.global.utils.exception
 * @class       ErrorResponse
 * @description 예외 발생 시 반환될 에러 메시지 응답 형식을 정의
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

import org.springframework.http.HttpStatusCode;

public record ErrorResponse (
        HttpStatusCode status,
        Object message
) {

}
