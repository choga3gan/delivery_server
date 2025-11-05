/**
 * @package     src/main/java/com/choga3gan/delivery/order
 * @class       OrderService
 * @description 주문 생성 및 처리
 *
 * @author      leehanbeen
 * @since
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 *  2025.11.03   leehanbeen       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.order.service;

import com.choga3gan.delivery.order.domain.*;
import com.choga3gan.delivery.order.dto.OrderRequestDto;
import com.choga3gan.delivery.order.dto.OrderResponseDto;
import com.choga3gan.delivery.order.dto.OrderUpdateStatusRequestDto; // 새로 필요
import com.choga3gan.delivery.order.domain.OrderCancelException;
import com.choga3gan.delivery.order.domain.OrderItemNotExistException;
import com.choga3gan.delivery.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    // ==========================================================
    // 1. 새로운 주문 생성 및 결제 요청 (POST v1/orders)
    // ==========================================================
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        if (requestDto.getOrderItems() == null || requestDto.getOrderItems().isEmpty()) {
            throw new OrderItemNotExistException();
        }

        // TODO: 실제 상품 정보를 조회하여 OrderItem에 Price를 설정하는 로직 필요
        List<OrderItem> orderItems = requestDto.getOrderItems().stream()
                .map(itemDto -> new OrderItem(itemDto.getItemId(), itemDto.getQuantity(), new Price(10000))) // 임시 가격
                .collect(Collectors.toList());

        Price totalPrice = calculateTotalPrice(orderItems);

        Order newOrder = Order.builder()
                .usedId(requestDto.getUserId())
                .storeId(requestDto.getStoreId())
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .userAddress(requestDto.getUserAddress())
                .orderStatus(OrderStatus.ORDER_PENDING) // 초기 상태: 결제 대기
                .build();

        Order savedOrder = orderRepository.save(newOrder);

        // TODO: (중요) 결제 시스템에 결제 요청을 보내는 로직 (이벤트 또는 외부 API 호출) 구현 필요

        return OrderResponseDto.from(savedOrder);
    }

    // ==========================================================
    // 2. 특정 사용자의 전체 주문 목록 조회 (GET v1/orders?userId=...)
    // ==========================================================
    public List<OrderResponseDto> getOrdersByUserId(UUID userId) {
        // TODO: OrderRepository에 findByUsedId(UUID userId) 메서드 추가 및 사용
        List<Order> orders = orderRepository.findAll().stream() // 임시로 전체 조회 사용
                .filter(o -> o.getUsedId().equals(userId))
                .collect(Collectors.toList());

        if (orders.isEmpty()) {
            // 사용자에게 해당 주문이 없음을 알림
            return List.of();
        }

        return orders.stream()
                .map(OrderResponseDto::from)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // 3. 특정 주문의 상세 정보 단일 조회 (GET v1/orders/{orderId})
    // ==========================================================
    public OrderResponseDto getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 ID를 찾을 수 없습니다: " + orderId));
        return OrderResponseDto.from(order);
    }

    // ==========================================================
    // 4. 특정 주문에 대한 환불 또는 취소 요청 (DELETE v1/orders/{orderId}/cancel)
    // ==========================================================
    @Transactional
    public OrderResponseDto cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 ID를 찾을 수 없습니다: " + orderId));

        // **[요구사항 3 반영] 주문 취소 로직**
        LocalDateTime orderCreationTime = order.getCreatedAt();
        LocalDateTime cancelDeadline = orderCreationTime.plusMinutes(5);

        if (LocalDateTime.now().isAfter(cancelDeadline) && order.getOrderStatus() != OrderStatus.ORDER_PENDING) {
            throw new OrderCancelException("주문 접수 후 5분이 초과되어 취소할 수 없습니다.");
        }

        // 상태별 취소 처리
        if (order.getOrderStatus() == OrderStatus.ORDER_PENDING) {
            // 입금 확인 전: ORDER_CANCEL
            order.changeStatus(OrderStatus.ORDER_CANCEL);
        } else if (order.getOrderStatus() == OrderStatus.ORDER_ACCEPT) {
            // 입금 완료 후: ORDER_REFUND (결제 취소 진행 필요)
            order.changeStatus(OrderStatus.ORDER_REFUND);
            // TODO: 결제 취소 이벤트를 발생시켜야 합니다. (외부 시스템 연동)
        } else {
            throw new OrderCancelException("현재 상태(" + order.getOrderStatus() + ")에서는 취소/환불 요청이 불가능합니다.");
        }

        return OrderResponseDto.from(order);
    }

    // ==========================================================
    // 5. 주문 상태 업데이트 (관리자용) (PATCH v1/orders/{orderId}/status)
    // ==========================================================
    @Transactional
    public OrderResponseDto updateOrderStatus(UUID orderId, OrderUpdateStatusRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 ID를 찾을 수 없습니다: " + orderId));

        OrderStatus newStatus = requestDto.getStatus();

        // **[요구사항 4 반영] 배송중 주문 상태 변경 조건 확인**
        if (newStatus == OrderStatus.IN_DELIVERY && order.getOrderStatus() == OrderStatus.ORDER_PENDING) {
            throw new IllegalStateException("입금 확인(ORDER_ACCEPT) 전에는 '배송 중' 상태로 변경할 수 없습니다.");
        }

        order.changeStatus(newStatus);

        return OrderResponseDto.from(order);
    }

    // 주문 상품 목록을 받아 총 금액을 계산합니다.
    private Price calculateTotalPrice(List<OrderItem> orderItems) {
        int total = orderItems.stream()
                .mapToInt(item -> item.getTotalPrice().getValue())
                .sum();
        return new Price(total);
    }
}
/*import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderStatus;
import com.choga3gan.delivery.order.dto.OrderRequestDto;
import com.choga3gan.delivery.order.dto.OrderResponseDto;
import com.choga3gan.delivery.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //=====================
    //1. 새로운 주문 생성 및 결제 요청(POST v1/orders)
    //=====================

    @Transactional
    public OrderResponseDto createAndRequestPayment(OrderRequestDto dto) {
        // 1. 주문 객체 생성 (초기 상태: PENDING)
        Order newOrder = new Order(dto.getUserId(), dto.getQuantity(), dto.getTotalPrice(),
                dto.getOrderStatus(), dto.getUsedId(), dto.getUserAddress(), dto.getStoreId(),
                dto.isReviewed());

        // 2. 주문 저장 (실제로는 결제 시스템 호출 로직이 필요)
        Order savedOrder = orderRepository.save(newOrder);

        // 3. (가정) 결제 시스템에 요청 후 성공하면 상태 변경
        // savedOrder.changeStatus(OrderStatus.PROCESSING);

        return new OrderResponseDto(savedOrder);
    }

    // ==========================================================
    // 2. 특정 주문의 상세 정보 단일 조회 (GET v1/orders/{orderId})
    // ==========================================================
    public OrderResponseDto getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID " + orderId + "를 찾을 수 없습니다."));

        return new OrderResponseDto(order);
    }

    // ==========================================================
    // 3. 특정 사용자의 전체 주문 목록 조회 (GET v1/orders)
    // (실제로는 Security Context에서 userId를 가져와 사용해야 하지만,
    // 여기서는 파라미터로 받거나 전체를 조회하는 것으로 가정합니다.)
    // ==========================================================
    public List<OrderResponseDto> getUserOrders(Long userId) {
        // 실제 API는 Security Context에서 얻은 현재 로그인된 사용자의 ID를 사용해야 합니다.
        // List<Order> orders = orderRepository.findByUserId(userId);
        List<Order> orders = orderRepository.findAll(); // 테스트를 위해 전체 조회

        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // 4. 특정 주문에 대한 환불 또는 취소 요청 (DELETE v1/orders/{orderId}/cancel)
    // ==========================================================
    @Transactional
    public OrderResponseDto requestCancelOrRefund(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID " + orderId + "를 찾을 수 없습니다."));

        // 도메인 객체의 취소 메서드 호출
        order.cancel();
        // 실제로는 환불 로직, 재고 복구 로직 등이 추가됩니다.

        return new OrderResponseDto(order);
    }

    // ==========================================================
    // 5. 주문 상태 업데이트 (관리자용) (PATCH v1/orders/{orderId}/status)
    // ==========================================================
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus order_status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID " + orderId + "를 찾을 수 없습니다."));

        // 도메인 객체의 상태 변경 메서드 호출
        order.changeStatus(order_status);

        return new OrderResponseDto(order);
    }


}*/
