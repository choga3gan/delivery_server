package com.choga3gan.delivery.user.service;

import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.dto.SignupRequest;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void SignUp(SignupRequest req){

        User user = User.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .username(req.username())
                .build();

        userRepository.save(user);
    }
}
