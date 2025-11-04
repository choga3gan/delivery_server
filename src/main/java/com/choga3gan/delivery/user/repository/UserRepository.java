package com.choga3gan.delivery.user.repository;

import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
