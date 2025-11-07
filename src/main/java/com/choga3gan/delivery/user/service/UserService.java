package com.choga3gan.delivery.user.service;

import com.choga3gan.delivery.global.jwt.TokenDto;
import com.choga3gan.delivery.global.jwt.TokenService;
import com.choga3gan.delivery.global.utils.service.SecurityUtilService;
import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.dto.*;
import com.choga3gan.delivery.user.exception.AuthHeaderException;
import com.choga3gan.delivery.user.exception.RoleNotFoundException;
import com.choga3gan.delivery.user.exception.UserException;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.RoleRepository;
import com.choga3gan.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final SecurityUtilService securityUtil;

    /**
     * 회원가입
     * @param  SignupRequest
     * @return
     */
    @Transactional
    public void SignUp(SignupRequest req){

        // 비밀번호 학인
        if(!req.password().equals(req.confirmPassword())){
            throw new UserException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        // 이메일 중복확인
        if(userRepository.existsByEmail(req.email())){
            throw new UserException("이미 존재하는 이메일 입니다.");
        }

        // 기본 역할 할당
        Role defaultRole = roleRepository.findByRoleName("ROLE_CUSTOMER")
                .orElseThrow(RoleNotFoundException::new);

        // 유저 생성
        User user = User.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .username(req.username())
                .role(defaultRole)
                .publicInfo(true)
                .build();

        userRepository.save(user);
    }

    /**
     * 로그인
     * @param LoginRequest
     * @return LoginResponse
     */
    @Transactional
    public LoginResponse login(LoginRequest req){

        // 사용자 조회
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(UserNotFoundException::new);

        // 비밀번호 검증
        if(!encoder.matches(req.getPassword(), user.getPassword())){
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

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

        // 토큰 재발급
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
    @Transactional
    public void changeUserRole(String userName, String roleName){
        User user = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(RoleNotFoundException::new);

        // 사용자 역할 변경
        user.changeRoles(role);
    }

    /**
     * 사용자 정보 공개 여부
     * 공개 Y / 비공개 N
     * @param
     * @return
     */


    /**
     * 사용자 상세 조회
     * @param
     * @return
     */
    @Transactional
    public UserResponse getUsersDetail(UUID userId){
      User user = userRepository.findById_Id(userId).orElseThrow(UserNotFoundException::new);

      return UserResponse.from(user);
    }

    /**
     * 사용자 목록 조회
     * @param
     * @return
     */
    @Transactional
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();

        // 유저 객체 받아서 매핑 후 리스트로 반환
        return users.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 사용자 등록
     * @param
     * @return
     */
    @Transactional
    public void createUser(UserCreateRequest req){

        // 이메일 중복 검사
        if(userRepository.existsByEmail(req.email())){
            throw new UserException("이미 존재하는 이메일입니다.");
        }

        // 사용자 이름 중복 검사
        if(userRepository.existsByUsername(req.username())){
            throw new UserException("이미 존재하는 사용자 이름입니다.");
        }

        // 기본 역할 할당
        Role defaultRole = roleRepository.findByRoleName("ROLE_CUSTOMER")
                .orElseThrow(RoleNotFoundException::new);

        // 유저 생성
        User user = User.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .username(req.username())
                .role(defaultRole)
                .publicInfo(true)
                .build();

        userRepository.save(user);

    }

    /**
     * 사용자 수정
     * @param
     * @return
     */
    @Transactional
    public UserResponse updateUser(UUID userId, UserUpdateRequest req){

        User user = userRepository.findById_Id(userId).orElseThrow(UserNotFoundException::new);

        String enPassword = null;

        if(req.password() != null &&  !req.password().isBlank()){
            enPassword = encoder.encode(req.password());
        }

        user.updateUser(req.email(), enPassword,req.publicInfo());

        return UserResponse.from(user);
    }

    /**
     * 사용자 삭제
     * @param
     * @return
     */
    public void userDelete(UUID userId){
        User user = userRepository.findById_Id(userId).orElseThrow(UserNotFoundException::new);
        user.softDelete(securityUtil.getCurrentUsername());
    }
}
