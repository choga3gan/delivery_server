/**
 * @package     com.choga3gan.delivery.global.jwt
 * @class       LoginFilter
 * @description HTTP 요청이 있을때 헤더에 담긴 JWT 토큰을 인증하는 필터
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
package com.choga3gan.delivery.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component // 해당 클래스를 Bean 으로 등록하게 함
@RequiredArgsConstructor // 생성자 자동 생성
public class LoginFilter extends GenericFilterBean {

    // JWT 관련 호직을 처리할  TokenService를 주입받음
    private final TokenService service;

    /**
     * 모든 요청마다 자동 호출
     * GenericFilterBean의 doFilter를 오버라이드
     * 요청 헤더
     *      Authentication: Bearer 토큰
     *
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        authenticate((HttpServletRequest) request);

        // 다음 필터로 넘김
        chain.doFilter(request, response);
    }

    /**
     * 요청의 Authorization 헤더에서 토큰을 꺼내 TokenService로 넘김
     * @param
     * @return
     */
    private void authenticate(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer")) {
            return;
        }

        service.authenticate(authorization.substring(7));
    }
}
