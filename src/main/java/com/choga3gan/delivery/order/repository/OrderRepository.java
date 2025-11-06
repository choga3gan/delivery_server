package com.choga3gan.delivery.order.repository;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, QuerydslPredicateExecutor<Order> {
    // 사용자 ID로 주문 목록을 조회하는 메서드를 추가할 수 있습니다.
    // List<Order> findByUsedId(UUID usedId);
    Optional<Order> findByOrderId(UUID orderId);
    List<Order> findByUserIdAndOrderItems_StoreIdAndOrderStatusAndReviewed(UUID userId, UUID storeId, OrderStatus orderStatus, boolean reviewed);
}

