package com.choga3gan.delivery.order.repository;

import com.choga3gan.delivery.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, QuerydslPredicateExecutor<Order> {
    // 사용자 ID로 주문 목록을 조회하는 메서드를 추가할 수 있습니다.
    // List<Order> findByUsedId(UUID usedId);
}

