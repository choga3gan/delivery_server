package com.choga3gan.delivery.user;

import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
public class RoleInsertDB {
    @Autowired
    RoleRepository repository;

    @Test
    void insertRoles() {

        List<Role> roles = new ArrayList<>();

        roles.add(Role.builder()
                .roleName("MASTER")
                .roleDescription("관리자")
                .build());

        roles.add(Role.builder()
                .roleName("ROLE_OWNER")
                .roleDescription("오너")
                .build());

        roles.add(Role.builder()
                .roleName("ROLE_MANAGER")
                .roleDescription("매니저")
                .build());

        roles.add(Role.builder()
                .roleName("ROLE_STAFF")
                .roleDescription("직원")
                .build());

        roles.add(Role.builder()
                .roleName("ROLE_CUSTOMER")
                .roleDescription("소비자")
                .build());

        repository.saveAllAndFlush(roles);
    }
}
