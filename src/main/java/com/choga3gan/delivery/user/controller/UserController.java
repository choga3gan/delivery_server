/**
 * @package     com.choga3gan.delivery.user.controller
 * @class       UserController
 * @description 회원 컨트롤러
 *
 * @author      hakjun
 * @since       2025. 11. 3.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 3.        hakjun       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.user.controller;

import com.choga3gan.delivery.global.security.UserDetailsImpl;
import com.choga3gan.delivery.user.dto.*;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.choga3gan.delivery.user.service.UserService;
import com.choga3gan.delivery.user.validator.SignupValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * 회원가입
     * @param
     * @return
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@Valid @RequestBody SignupRequest req) {
        new SignupValidator().validate(req, userRepository); // 값 검증
        userService.SignUp(req);
    }

    /**
     * 로그인
     * @param
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse response = userService.login(req);
        return ResponseEntity.ok(response);
    }

    /**
     * 토큰 재발급
     * @param
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshRequest request) {
        LoginResponse response = userService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    /**
     * 로그아웃
     * @param
     * @return
     */
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody LogoutRequest request) {

        userService.logout(authorizationHeader, request.getRefreshToken());
        return  ResponseEntity.ok().build();
    }

    /**
     * 사용자 목록 조회
     * @param
     * @return
     */

    /**
     * 사용자 상세 조회
     * @param
     * @return
     */

    /**
     * 사용자 등록
     * @param
     * @return
     */

    /**
     * 사용자 수정
     * @param
     * @return
     */

    /**
     * 사용자 삭제
     * @param
     * @return
     */


    //테스트용
    @GetMapping("profile")
    public void profile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("UserDetails: {}",userDetails);
    }

    @PreAuthorize("hasAnyRole('MANAGER','MASTER')")
    @GetMapping("profile/{username}")
    public void viewProfile(@PathVariable("username") String username) {
        log.info("UserDetails: {}",username);
    }
}
