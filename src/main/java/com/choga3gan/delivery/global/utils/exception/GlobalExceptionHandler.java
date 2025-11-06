/**
 * @package     com.choga3gan.delivery.user.global.utils.exception
 * @class       GlobalExceptionHandler
 * @description 모든 RestAPI Controller에서 발생하는 응답을 반환하고 종료되는 모든 예외에 대하여 처리하는 Advice
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

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.net.BindException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice("com.choga3gan.delivery")
public class GlobalExceptionHandler {

    /**
     * 검증 시 발생할 수 있는 예외를 처리하는 메서드
     * 400(Bad Request) 상태 코드를 반환
     *
     * @return 상태코드와 에러 메시지
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {

        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        Object message = ex.getMessage();

        if (
                ex instanceof MethodArgumentNotValidException ||
                ex instanceof BindException ||
                ex instanceof ConstraintViolationException
        ) {
            message = "입력된 형식이 올바르지 않습니다.";
        } else if (ex instanceof MissingServletRequestParameterException) {
            message = "요청 파라미터가 누락되었습니다.";
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            message = "요청 파라미터의 타입이 일치하지 않습니다.";
        }
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    /**
     * JPA 조회 시 조회 대상이 없을 경우 발생할 수 있는 예외를 처리하는 메서드
     * 404(NOT FOUND) 상태 코드를 반환
     *
     * @return 상태 코드와 에러 메시지
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        Object message = "대상이 존재하지 않습니다.";
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    /**
     * 권한이 없는 사용자가 접근을 시도할 경우 발생할 수 있는 예외를 처리하는 메서드
     * 403(FORBIDDEN) 상태 코드를 반환
     *
     * @return 상태 코드와 에러 메시지
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenialException(Exception ex) {
        HttpStatusCode status = HttpStatus.FORBIDDEN;
        Object message = "접근 권한이 부족합니다.";
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    /**
     * 토큰이 존재하지 않거나, 만료된 토큰으로 접근을 시도할 경우 발생할 수 있는 예외를 처리하는 메서드
     * 401(UNAUTHORIZED) 상태 코드를 반환
     *
     * @return 상태 코드와 에러 메시지
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        Object message = "로그인/토큰 인증이 실패하였습니다.";
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    /**
     * 예상하지 못한 서버 에러에 대한 예외를 처리하는 메서드
     * 500(INTERNAL SERVER ERROR) 상태 코드를 반환
     *
     * @return 상태 코드와 에러 메시지
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = ex.getMessage();
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    /**
     * 직접 설정한 예외(Custom Exception)를 처리하는 메서드
     * 직접 설정한 상태 코드를 반환
     *
     * @return 상태 코드와 에러 메시지
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatus()).body(new ErrorResponse(ex.getStatus(), ex.getMessage()));
    }
}
