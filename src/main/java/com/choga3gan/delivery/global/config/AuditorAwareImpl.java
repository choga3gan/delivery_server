package com.choga3gan.delivery.global.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null || !authentication.isAuthenticated()) {
            return Optional.of("system"); // 로그인 안되있으면 system
        }
        return Optional.ofNullable(authentication.getName()); // 로그인된 username 반환
    }
}
