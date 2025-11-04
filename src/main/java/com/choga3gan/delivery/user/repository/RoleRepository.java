package com.choga3gan.delivery.user.repository;

import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.domain.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, RoleId> {
    Optional<Role> findById(UUID roleId);

    // 삭제항목 제외
    boolean existsByRoleNameAndDeletedAtIsNull(String name);
}
