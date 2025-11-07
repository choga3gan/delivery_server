package com.choga3gan.delivery.order.controller;

import com.choga3gan.delivery.global.security.UserDetailsImpl;
import com.choga3gan.delivery.order.dto.OrderDto;
import com.choga3gan.delivery.order.dto.OrderRequest;
import com.choga3gan.delivery.order.dto.OrderResponse;
import com.choga3gan.delivery.order.dto.OrderUpdateRequest;
import com.choga3gan.delivery.order.service.OrderDetailsService;
import com.choga3gan.delivery.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "주문 API", description = "주문 신규 생성, 주문 조회, 주문서 수정, 주문 삭제 기능을 위한 API")
@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailsService detailsService;


    @Operation(
            summary = "신규 주문 생성",
            description = """
            이 Controller 메서드는 로그인된 사용자를 위해 새로운 주문을 생성하는 REST API 엔드포인트입니다.
            """
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUser().getId().getId();
        UUID orderId = orderService.createOrder(userId, req);

        return new OrderResponse(orderId);
    }

    @Operation(
            summary = "주문 정보 업데이트",
            description = """
                    이 Controller 메서드는 HTTP PATCH요청을 받아 특정 주문(orderId)의 정보를 업데이트합니다.
            """
    )
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@PathVariable("orderId") UUID orderId, @Valid @RequestBody OrderUpdateRequest req, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        orderService.updateOrder(getUserId(userDetails), orderId, req.name(), req.email(), req.address());

    }

    @Operation(
            summary = "주문 취소, 환불",
            description = """
                    이 Controller 메서드는 HTTP PATCH요청을 받아 특정 주문(orderId)을 취소, 환불합니다.
            """
    )
    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") UUID orderId) {

        orderService.cancelOrder(orderId);
    }


    @Operation(
            summary = "매장 관리자가 주문된 주문을 승인, 거부",
            description = """
                    이 Controller 메서드는 HTTP PATCH요청을 받아 특정 주문(orderId)의 수락 상태를 변경합니다.
            """
    )
    @PatchMapping("/{orderId}/status")
    public void orderAccept(@PathVariable("orderId") UUID orderId, @RequestParam("accept") boolean accept) {
        orderService.acceptOrder(orderId, accept);
    }


    @Operation(
            summary = "매장의 주문 목록을 조회합니다",
            description = """
                    이 Controller 메서드는 HTTP GET요청을 받아 특정 사용자의 주문 목록을 조회합니다.
            """
    )
    @GetMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public List<OrderDto> orderList(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return detailsService.findAll(getUserId(userDetails));
    }


    @Operation(
            summary = "관리자가 매장별 주문 목록을 확인합니다",
            description = """
                    이 Controller 메서드는 HTTP GET요청을 받아 특정 상점의 주문 목록을 조회합니다.
            """
    )
    @GetMapping("/{storeId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public List<OrderDto> orderAdminList(@PathVariable("storeId") UUID storeId) {
        return detailsService.findAllByStoreId(storeId);
    }

    private UUID getUserId(UserDetailsImpl userDetails) {
        return userDetails.getUser().getId().getId();
    }
}
