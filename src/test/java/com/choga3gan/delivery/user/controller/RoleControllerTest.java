package com.choga3gan.delivery.user.controller;

import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.dto.RoleDto;
import com.choga3gan.delivery.user.repository.RoleRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private RoleRepository repository;

    @Test
    @DisplayName("역할 생성 테스트")
    @MockUser(roles = {"USER", "MASTER"})
    void createRoleTest() throws Exception {
        RoleDto req = new RoleDto("ROLE_USER","사용자");
        String body = om.writeValueAsString(req);

        mockMvc.perform(post("/v1/roles")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("역할 조회 테스트")
    //MockUser(roles = {"USER", "MASTER"})
    void selectRoletest() throws Exception {
        mockMvc.perform(get("/v1/roles"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void test() {
        System.out.println("테스트 -------");
        List<Role> roles = repository.findAll();
        System.out.println(roles);
        roles.forEach(System.out::println);
    }
}
