package com.choga3gan.delivery.store.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class StoreAccessAspect {

    private final StoreAuthorizationService storeAuthorizationService;

    @Before("@annotation(checkStoreAccess)")
    public void checkStoreAccess(JoinPoint joinPoint, CheckStoreAccess checkStoreAccess) {
        String paramName = checkStoreAccess.value();
        UUID storeId = getStoreIdFromArgs(joinPoint, paramName);

        storeAuthorizationService.validateStoreAccess(storeId);
    }

    private UUID getStoreIdFromArgs(JoinPoint joinPoint, String paramName) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(paramName)) {
                if (args[i] instanceof UUID id) {
                    return id;
                }
                // 인자가 String 타입이라면 UUID로 변환 시도 (경로 변수 등으로 들어왔을 경우)
                else if (args[i] instanceof String idString) {
                    try {
                        return UUID.fromString(idString);
                    } catch (IllegalArgumentException e) {
                        // UUID 변환에 실패한 경우
                        throw new IllegalArgumentException(
                                "'" + paramName + "' 파라미터는 유효한 UUID 형식이 아닙니다: " + idString, e
                        );
                    }
                }
            }
        }

        throw new IllegalArgumentException(
                "storeId 파라미터를 찾을 수 없습니다. @CheckStoreAccess(\"파라미터명\")을 확인하세요."
        );
    }
}
