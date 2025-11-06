package com.choga3gan.delivery.user.service;

import com.choga3gan.delivery.global.jwt.TokenDto;
import com.choga3gan.delivery.global.jwt.TokenService;
import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.dto.LoginRequest;
import com.choga3gan.delivery.user.dto.LoginResponse;
import com.choga3gan.delivery.user.dto.SignupRequest;
import com.choga3gan.delivery.user.exception.AuthHeaderException;
import com.choga3gan.delivery.user.exception.RoleNotFoundException;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.RoleRepository;
import com.choga3gan.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    /**
     * 회원가입
     * @param  SignupRequest
     * @return
     */
    @Transactional
    public void SignUp(SignupRequest req){

        // 이메일 중복확인

        // 기본 역할 할당
        Role defaultRole = roleRepository.findByRoleName("ROLE_CUSTOMER")
                .orElseThrow(RoleNotFoundException::new);

        User user = User.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .username(req.username())
                .role(defaultRole)
                .build();

        userRepository.save(user);
    }

    /**
     * 로그인
     * @param TokenDto
     * @return LoginResponse
     */
    @Transactional
    public LoginResponse login(LoginRequest req){
        // 사용자 조회
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(UserNotFoundException::new);

        // TODO : 비밀번호 검증 로직

        //JWT 토큰 생성
        TokenDto tokenDto = tokenService.create(user.getUsername());

        // refresh 토큰 DB에 저장
        user.updateRefreshToken(tokenDto.refreshToken());
        userRepository.save(user);

        return LoginResponse.builder()
                .access_token(tokenDto.accessToken())
                .refresh_token(tokenDto.refreshToken())
                .username(user.getUsername())
                .build();
    }

    /**
     * 토큰 재발급
     * @param
     * @return
     */
    @Transactional
    public LoginResponse refreshToken(String refreshToken){
        TokenDto tokenDto = tokenService.refresh(refreshToken);

        // 토큰 식별자 가져오기
        String username = tokenService.getTokenSubject(refreshToken);

        // 사용자 정보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return LoginResponse.builder()
                .access_token(tokenDto.accessToken())
                .refresh_token(tokenDto.refreshToken())
                .username(user.getUsername())
                .build();
    }

    /**
     * 로그아웃
     * @param
     * @return
     */
    @Transactional
    public void logout(String authorizationHeader, String refreshToken){
        if(!authorizationHeader.equals("Bearer ")){
            throw new AuthHeaderException();
        }
        // Bearer 제거
        String token = authorizationHeader.substring(7);

        // 토큰 식별자 가져오기
        String username = tokenService.getTokenSubject(token);

        // 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        // refreshToken 비교 및 삭제
        if(user.getRefreshToken() != null && user.getRefreshToken().equals(token)){
            user.deleteRefreshToken();
            userRepository.save(user);
        } else {
            throw new AuthHeaderException("리프레시 토큰이 유효하지 않습니다.");
        }
    }

    /**
     * 사용자 역할 변경
     * @param
     * @return
     */
    public void userRoleChange(User user, Role role){

    }
}
