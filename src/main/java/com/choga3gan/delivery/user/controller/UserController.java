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
import com.choga3gan.delivery.user.dto.SignupRequest;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.choga3gan.delivery.user.service.UserService;
import com.choga3gan.delivery.user.validator.SignupValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@Valid @RequestBody SignupRequest req) {
        new SignupValidator().validate(req, userRepository); // 값 검증

        service.SignUp(req);
    }

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
