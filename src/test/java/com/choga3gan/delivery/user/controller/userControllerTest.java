package com.choga3gan.delivery.user.controller;

import com.choga3gan.delivery.global.jwt.TokenDto;
import com.choga3gan.delivery.global.jwt.TokenService;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.dto.SignupRequest;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.choga3gan.delivery.user.test.MockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class userControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    UserRepository repository;

    @Autowired
    TokenService tokenService;

    @Test
    @DisplayName("회원가입 테스트, 가입 성공시 201")
    @Transactional
    void signupTest() throws Exception {

        SignupRequest req = new SignupRequest("user01", "user01@test.org", "_aA123456", "_aA123456");
        String body = om.writeValueAsString(req);

        mockMvc.perform(post("/v1/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated());


        User user = repository.findByUsername(req.username()).orElse(null);
        System.out.println(user);


        TokenDto token = tokenService.create(req.username());
        tokenService.validate(token.accessToken());

        mockMvc.perform(get("/v1/users/profile/user001")
                        .header("Authorization", "Bearer " + token.accessToken()))
                .andDo(print());

    }

    // 토큰 없이 테스트 하는 방식
    @Test
    @MockUser(roles = {"USER", "MASTER"})
    void adminProfileTest() throws Exception {
        mockMvc.perform(get("/v1/users/profile/user001"))
                .andDo(print());
    }
}
