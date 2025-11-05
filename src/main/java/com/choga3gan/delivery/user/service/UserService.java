package com.choga3gan.delivery.user.service;

import com.choga3gan.delivery.global.jwt.TokenDto;
import com.choga3gan.delivery.global.jwt.TokenService;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.dto.LoginRequest;
import com.choga3gan.delivery.user.dto.LoginResponse;
import com.choga3gan.delivery.user.dto.SignupRequest;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void SignUp(SignupRequest req){

        // 이메일 중복확인

        User user = User.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .username(req.username())
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

        // TODO : refresh 토큰 DB에 저장

        return LoginResponse.builder()
                .access_token(tokenDto.accessToken())
                .refresh_token(tokenDto.refreshToken())
                .username(user.getUsername())
                .build();
    }
}
