package com.choga3gan.delivery.order.controller;

import com.choga3gan.delivery.order.service.OrderService;
import com.choga3gan.delivery.order.dto.OrderRequestDto;
import com.choga3gan.delivery.order.dto.OrderResponseDto;
import com.choga3gan.delivery.order.dto.OrderUpdateStatusRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // POST v1/orders
    @Operation(summary = "새로운 주문 생성 및 결제 요청")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto response = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET v1/orders?userId={userId}
    @Operation(summary = "특정 사용자의 전체 주문 목록 조회")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@RequestParam("userId") UUID userId) {
        List<OrderResponseDto> response = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // GET v1/orders/{orderId}
    @Operation(summary = "특정 주문의 상세 정보 단일 조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderDetails(@PathVariable UUID orderId) {
        OrderResponseDto response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    // DELETE v1/orders/{orderId}/cancel
    @Operation(summary = "특정 주문에 대한 환불 또는 취소 요청")
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable UUID orderId) {
        OrderResponseDto response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

    // PATCH v1/orders/{orderId}/status
    @Operation(summary = "주문 상태 업데이트 (관리자용)")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable UUID orderId,
                                                              @Valid @RequestBody OrderUpdateStatusRequestDto requestDto) {
        OrderResponseDto response = orderService.updateOrderStatus(orderId, requestDto);
        return ResponseEntity.ok(response);
    }
}
/*import com.choga3gan.delivery.order.domain.OrderStatus;
import com.choga3gan.delivery.order.dto.OrderRequestDto;
import com.choga3gan.delivery.order.dto.OrderResponseDto;
import com.choga3gan.delivery.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Management(주문 관리)", description = "주문 생성, 조회, 취소, 상태 변경 API")
@RestController
@RequestMapping("v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ==========================================================
    // 1. 새로운 주문 생성 및 결제 요청 (POST v1/orders)
    // ==========================================================
    @Operation(summary = "새로운 주문 생성 및 결제 요청", description = "총 결제 금액과 사용자 ID를 받아 주문을 생성하고 결제를 요청합니다.")
    @PostMapping // POST v1/orders
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto response = orderService.createAndRequestPayment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==========================================================
    // 2. 특정 사용자의 전체 주문 목록 조회 (GET v1/orders)
    // ==========================================================
    @Operation(summary = "특정 사용자의 전체 주문 목록 조회", description = "현재 로그인한 사용자(또는 userId)의 모든 주문 목록을 조회합니다.")
    @GetMapping // GET v1/orders
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(
            @Parameter(description = "주문을 조회할 사용자 ID (실제는 Security Context 사용)", example = "100")
            @RequestParam(required = false) Long userId) {

        // 실제는 여기서 Security Context에서 userId를 가져와 사용합니다.
        List<OrderResponseDto> response = orderService.getUserOrders(userId);
        return ResponseEntity.ok(response);
    }


    // ==========================================================
    // 3. 특정 주문의 상세 정보 단일 조회 (GET v1/orders/{orderId})
    // ==========================================================
    @Operation(summary = "특정 주문의 상세 정보 단일 조회", description = "주문 ID를 통해 단일 주문 정보를 반환합니다.")
    @GetMapping("/{orderId}") // GET v1/orders/{orderId}
    public ResponseEntity<OrderResponseDto> getOrderDetails(
            @Parameter(description = "조회할 주문 ID", example = "1")
            @PathVariable Long orderId) {

        OrderResponseDto response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // 4. 특정 주문에 대한 환불 또는 취소 요청 (DELETE v1/orders/{orderId}/cancel)
    // ==========================================================
    @Operation(summary = "주문 취소/환불 요청", description = "주문 ID를 받아 주문을 취소하고 환불 처리합니다.")
    @DeleteMapping("/{orderId}/cancel") // DELETE v1/orders/{orderId}/cancel
    public ResponseEntity<OrderResponseDto> requestCancel(
            @Parameter(description = "취소할 주문 ID", example = "1")
            @PathVariable Long orderId) {

        OrderResponseDto response = orderService.requestCancelOrRefund(orderId);
        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // 5. 주문 상태 업데이트 (관리자용) (PATCH v1/orders/{orderId}/status)
    // ==========================================================
    @Operation(summary = "주문 상태 업데이트 (관리자용)", description = "관리자가 주문 ID와 새로운 상태를 강제 업데이트합니다.")
    @PatchMapping("/{orderId}/status") // PATCH v1/orders/{orderId}/status
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @Parameter(description = "상태를 변경할 주문 ID", example = "1")
            @PathVariable Long orderId,
            @Parameter(description = "변경할 새로운 주문 상태 (PENDING, PROCESSING 등)", example = "SHIPPED")
            @RequestParam OrderStatus newStatus) {

        OrderResponseDto response = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(response);
    }

}*/
