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
        UUID storeId = UUID.fromString(paramName);

        storeAuthorizationService.validateStoreAccess(storeId);
    }

    private Long getStoreIdFromArgs(JoinPoint joinPoint, String paramName) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(paramName) && args[i] instanceof Long id) {
                return id;
            }
        }

        throw new IllegalArgumentException(
                "storeId 파라미터를 찾을 수 없습니다. @CheckStoreAccess(\"파라미터명\")을 확인하세요."
        );
    }
}
