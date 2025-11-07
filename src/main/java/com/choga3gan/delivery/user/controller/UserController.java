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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "회원 API", description = "회원 로그인, 로그아웃, 인증 관련 외 회원에 관련된 API")
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;



    @Operation(summary = "회원가입",
               description = """
                회원가입입니다.
                아이디는 소문자와 숫자만, 4자 이상 10자 이하로 입력해주세요.
                비밀번호는 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.
                """
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@Valid @RequestBody SignupRequest req) {
        new SignupValidator().validate(req, userRepository); // 값 검증
        userService.SignUp(req);
    }

    @Operation(summary = "로그인",
               description = """
                로그인 입니다.
                """
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse response = userService.login(req);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "토큰 재발급",
               description = """
                토큰 재발급입니다.
                """
    )
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshRequest request) {
        LoginResponse response = userService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃",
               description = """
                로그아웃입니다.
                """
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody LogoutRequest request) {

        userService.logout(authorizationHeader, request.getRefreshToken());
        return  ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 역할 변경",
               description = """
                사용자의 역할을 변경합니다.
                """
    )
    @PatchMapping("/role")
    public ResponseEntity<Void> changeUserRole(@RequestBody ChangeRoleRequest request) {
        userService.changeUserRole(request.getUserName(), request.getRoleName());
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "사용자 목록 조회",
               description = """
                사용자 목록을 조회합니다.
                """
    )
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUser() {
        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "사용자 상세 조회",
               description = """
                사용자 상세 조회 입니다.
                """
    )
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<UserResponse> getUserDetails(UUID userId) {
        UserResponse res = userService.getUsersDetail(userId);

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "사용자등록",
               description = """
                마스터 권한 유저가 사용하는 사용자 등록입니다.
                """
    )
    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest req){
        userService.createUser(req);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 수정
     * @param
     * @return
     */
    @PatchMapping("{userId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<UserResponse> patchUser(@PathVariable UUID userId,
            @RequestBody UserUpdateRequest req){
        userService.updateUser(userId, req);
        return ResponseEntity.ok().build();
    }


    /**
     * 사용자 삭제
     * @param
     * @return
     */
    @DeleteMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.userDelete(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인한 사용자 프로필 조회
     * @param  
     * @return 
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse res = userService.getUsersDetail(userDetails.getUser().getId().getId());

        return ResponseEntity.ok(res);
    }
    //테스트용
//    @GetMapping("profile")
//    public void profile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        log.info("UserDetails: {}",userDetails);
//    }
//
//    @PreAuthorize("hasAnyRole('MANAGER','MASTER')")
//    @GetMapping("profile/{username}")
//    public void viewProfile(@PathVariable("username") String username) {
//        log.info("UserDetails: {}",username);
//    }
}
