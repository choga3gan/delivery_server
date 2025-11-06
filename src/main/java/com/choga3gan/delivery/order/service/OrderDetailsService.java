package com.choga3gan.delivery.order.service;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.QOrder;
import com.choga3gan.delivery.order.dto.OrderDto;
import com.choga3gan.delivery.order.dto.OrderItemDto;
import com.choga3gan.delivery.store.domain.QStore;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {
    private final JPAQueryFactory queryFactory;
    private final StoreRepository storeRepository;


    /**
     * 주문서 한개 조회
     *
     * @param orderId
     * @return
     */
    public OrderDto findById(UUID orderId) {
        QOrder order = QOrder.order;
        Order item = queryFactory.selectFrom(order)
                .leftJoin(order.orderItems)
                .fetchJoin()
                .where(order.orderId.eq(orderId))
                .fetchFirst();

        return toOrderDto(item);
    }

    /**
     * 상점별 주문 목록 조회
     *
     * @param storeId
     * @return
     */
    public List<OrderDto> findAllByStoreId(UUID storeId) {
        QOrder order = QOrder.order;
        return queryFactory.selectFrom(order)
                .leftJoin(order.orderItems)
                .fetchJoin()
                .where(order.orderItems.any().storeId.eq(storeId))
                .orderBy(order.createdAt.desc())
                .fetch()
                .stream()
                .map(this::toOrderDto).toList();
    }

    /**
     * 상점별 주문 목록, 현재 오너의 상점으로 한정
     * @return
     */
    public List<OrderDto> findAll(UUID userId) {
        QStore store = QStore.store;
        Store s  = storeRepository.findOne(store.user.id.id.eq(userId)).orElse(null);
        if (s == null) return null;

        UUID storeId = s.getStoreId();
        return findAllByStoreId(storeId);

    }

    private OrderDto toOrderDto(Order order) {
        if (order == null) return null;

        List<OrderItemDto> orderItems = order.getOrderItems().stream().map(o -> OrderItemDto.builder()
                .storeId(o.getStoreId())
                .productId(o.getProductId())
                .quantity(o.getQuantity())
                .productPrice(o.getProductPrice())
                .productName(o.getProductName())
                .build()
        ).toList();
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .reviewed(order.isReviewed())
                .userId(order.getUserId())
                .userEmail(order.getUserEmail())
                .userAddress(order.getUserAddress())
                .createdAt(order.getCreatedAt())
                .items(orderItems)
                .build();
    }
}
