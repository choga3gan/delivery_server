/**
 * @package     com.choga3gan.delivery.global.jwt
 * @class       TokenService
 * @description JWT 토큰을 생성, 검증, 인증하는 서비스 클래스
 *              로그인 후 인증 유지하는 핵심 로직
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

import com.choga3gan.delivery.global.security.UserDetailsImpl;
import com.choga3gan.delivery.global.utils.exception.UnauthorizedException;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.micrometer.common.util.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
@EnableConfigurationProperties({JwtProperties.class}) // JwtProperties에 JWT 프로퍼티 매핑
public class TokenService {

    private final JwtProperties properties; // secret 키, 만료시간
    private final UserRepository repository;
    private final Key key; // 토큰 서명에 사용할 비밀 키 객체

    /**
     * 설정에서 읽은 secret을 디코딩해서 JWT용 서명키로 반환
     * @param
     * @return
     */
    public TokenService(JwtProperties properties, UserRepository repository) {
        this.properties = properties;
        this.repository = repository;

        //시크릿 값을 복호화 하여 키 변수에 할당
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 사용자 이름으로 토큰 발급
     * @param  username
     * @return
     */
    public TokenDto create(String username){

        //사용자 이름 가져옴
        User user = repository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        // 토큰 만료 시간
        // 엑세스 토큰
        Date now = new Date();
        Date accessExpire = new Date(now.getTime() + properties.getValidTime() * 1000L);
        Date refreshExpire = new Date(now.getTime() + properties.getValidTime() * 1000L);


        //엑세스 토큰 생성
        String accessToken = Jwts.builder()
                .setSubject(username) // 식별자
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessExpire)
                .compact();

        //리프레시 토큰 생성
        String refreshToken = Jwts.builder()
                .setSubject(username) // 식별자
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(refreshExpire)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expireTime(properties.getValidTime())
                .build();
    }

    /**
     * 토큰으로 인증 처리하는 메서드
     * @param  token
     * @return
     */
    public Authentication authenticate(String token){

        //토큰 유효성 검사
        validate(token);

        // JWT 문자열을 파싱, 서명을 검증하고 페이로드를 얻어옴
        Claims claims = Jwts.parser()// jwt 문자열 분석할 파서 생성
                .setSigningKey(key)// jwt를 검증할 키 생성
                .build() // 파서를 빌드함
                .parseClaimsJws(token) // jwt 문자열을 해석
                .getPayload(); // 페이로드 부분을 꺼냄

        // 역할 변경 이후 토큰 검증
        // 예) DB에서 owner role을 취소한 경우 바로 반영이 되어야 하는 경우는 토큰쪽 테이터보다는 직접 조회
        // 토큰 안의 subject로 실제 사용자 정보 조회
        User user = repository.findByUsername(claims.getSubject()).orElseThrow(UserNotFoundException::new);

        //user 엔티티를 UserDetails로 변환
        UserDetails userDetails = new UserDetailsImpl(user);

        /**
         * 인증 객체
         * Authentication 인터페이스는 spring security 에서 인증된 사용자 정보를 담는 표준 객체
         * UsernamePasswordAuthenticationToken 클래스는 Authentication 인터페이스를 구현한 구현체
         */
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        /**
         * 로그인 처리
         * SecurityContextHolder란 spring security의 전역 보관소
         * 현재 스레드(요청)에서 로그인한 사용자가 누군지 저장하는 곳
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    /**
     * 토큰 유효성 검사
     * 각 상황에 따라서 예외가 다르게 발생
     * @param  token
     * @return
     */
    public void validate(String token){
        String message = null;
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getPayload();

        } catch (ExpiredJwtException e) { // 토큰 유효시간 만료
            message = "토큰이 만료 되었습니다.";
        } catch (MalformedJwtException | SecurityException e) { // 토큰 변조
            message = "잘못된 형식의 토큰입니다.";
        } catch (UnsupportedJwtException e) { // 지원하지 않는 형식 토큰
            message = "지원하지 않는 형식입니다.";
        }

        // TODO : 토큰 만료 전에 명시적으로 로그아웃을 한 경우, 토큰이 유효해도 검증 실패 처리

        if (!StringUtils.isEmpty(message)) {// 임포트 확인하기
            throw new UnauthorizedException(message);
        }
        }


}
