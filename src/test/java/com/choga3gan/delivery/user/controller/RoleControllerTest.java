package com.choga3gan.delivery.user.controller;

import com.choga3gan.delivery.user.dto.RoleDto;
import com.choga3gan.delivery.user.service.RoleService;
import com.choga3gan.delivery.user.test.MockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RoleService roleService;

    @Test
    @DisplayName("역할 생성 테스트")
    @MockUser(roles = {"USER", "MASTER"})
    void createRoleTest() throws Exception {
        RoleDto req = new RoleDto("ROLE_CUSTOMER","소비자");
        String body = om.writeValueAsString(req);

        mockMvc.perform(post("/v1/roles")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
