package com.choga3gan.delivery.user.test;

import com.choga3gan.delivery.global.security.UserDetailsImpl;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.domain.UserId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.List;

public class WithMockSecurityContextFactory implements WithSecurityContextFactory<MockUser>
{
    @Override
    public SecurityContext createSecurityContext(MockUser anno) {

        // 유저 객체 생성
        User user = User.builder()
                .id(UserId.of())
                .username(anno.username())
                .email(anno.email())
                .build();

        // 생성된 유저 객체를 security 표준 객체로 반환
        UserDetails userDetails = new UserDetailsImpl(user);

        // 역할을 SimpleGrantedAuthority 리스트로 받아옴
        List<SimpleGrantedAuthority> authorities = Arrays.stream(anno.roles()).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();

        //
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
