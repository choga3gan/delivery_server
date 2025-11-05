/**
 * @package     com.choga3gan.delivery.global.config
 * @class       SecurityConfig
 * @description spring security의 보안 설정
 *              @EnableMethodSecurity 어노테이션으로 @PreAuthorize, @PostAuthorize, @Secured 같은 메서드 단위 권한 검사 어노테이션을 활성화시킴
 *
 * @author      hakjun
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        hakjun       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.global.config;

import com.choga3gan.delivery.global.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginFilter loginFilter;

    /**
     * spring security의 보안 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable()) //CSRF 공격 방지 기능을 끔(브라우저 세션 공격), JWT 사용하니 필요 없음
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)// security 필터 체인에 커스텀 필터 추가
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .exceptionHandling(c -> { // 예외처리
                    c.authenticationEntryPoint((req, res, e) -> {
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");// 로그인하지 않은 사용자가 접근 401
                    });
                    c.accessDeniedHandler((req, res, e) -> {
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"접근 권한이 없습니다.");// 권한이 없는 사용자가 접근 401
                    });
                })
                .authorizeHttpRequests(authorize -> { authorize
                        .requestMatchers("/api-docs/**","/v1/users/**","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html","/").permitAll()// 인증 없이 접근 가능한 경로
                        //.requestMatchers(HttpMethod.GET, "/v1/stores/**", "/v1/products/**").permitAll()// 공개 get API (매장 목목, 메뉴 보기 등)
                        .requestMatchers("/v1/users/profile").hasRole("USER") // 해당 경로는 해당 권한을 가진 사람만 접근 가능
                        .anyRequest().authenticated(); // 그 외 모든 요청은 인증 필요
                });
        return http.build();
    }

    /**
     * 비밀번호를 암호화 하기 위한 빈을 등록하는 코드
     * PasswordEncoder spring security에서 제공하는 비밀번호 암호화 인터페이스
     * 사용자가 입력한 평문 비밀번호를 DB에 저장하기 위해 암호화하는 구문
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
