package com.choga3gan.delivery.order.repository;

import com.choga3gan.delivery.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    // 사용자 ID로 주문 목록을 조회하는 메서드를 추가할 수 있습니다.
    // List<Order> findByUsedId(UUID usedId);
}




/*import com.choga3gan.delivery.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    //특정 사용자의 전체 주문 목록 조회(GET v1/orders)
    //List<Order> findByUseId(UUID userId);
    //Controller와 Service에서 사용자 ID를 RequestParam으로 받거나 Security Context에서 가져온다고 가정.
}*/
