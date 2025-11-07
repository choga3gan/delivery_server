package com.choga3gan.delivery.user.repository;

import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.domain.UserId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    /**
     * user 조회시 role을 즉시 조인해 가져옴
     * hibernate의 Lazy 로딩이 작동중인데 세션이 닫혀 프록시 객체를 초기화 할 수 없던 오류 수정
     */
    @EntityGraph(attributePaths = "role")
    Optional<User> findByUsername(String username);
    Optional<User> findById(UUID id); // 해당 부분 얘기하기
    Optional<User> findById_Id(UUID id); // 이걸로 바꿔야할듯?
    Optional<User> findByEmail(String email);
}
