package com.choga3gan.delivery.user.repository;

import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.domain.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, RoleId> {
}
