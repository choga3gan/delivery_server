package com.choga3gan.delivery.order.controller;

import com.choga3gan.delivery.global.security.UserDetailsImpl;
import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.dto.OrderDto;
import com.choga3gan.delivery.order.dto.OrderRequest;
import com.choga3gan.delivery.order.dto.OrderResponse;
import com.choga3gan.delivery.order.dto.OrderUpdateRequest;
import com.choga3gan.delivery.order.service.OrderDetailsService;
import com.choga3gan.delivery.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailsService detailsService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUser().getId().getId();
        UUID orderId = orderService.createOrder(userId, req);

        return new OrderResponse(orderId);
    }

    // 주문서 정보 확인
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@PathVariable("orderId") UUID orderId, @Valid @RequestBody OrderUpdateRequest req, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        orderService.updateOrder(getUserId(userDetails), orderId, req.name(), req.email(), req.address());

    }

    // 주문 취소, 환불
    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") UUID orderId) {

        orderService.cancelOrder(orderId);
    }


    /**
     * 매장 관리자가 주문된 주문서를 승인, 거부
     *
     * @param orderId
     * @param accept : true - 승인 ->
     */
    @PatchMapping("/{orderId}/status")
    public void orderAccept(@PathVariable("orderId") UUID orderId, @RequestParam("accept") boolean accept) {
        orderService.acceptOrder(orderId, accept);
    }

    @PatchMapping("/{orderId}/complete")
    public void orderComplete(@PathVariable("orderId") UUID orderId) {
        orderService.completeOrder(orderId);
    }


    /**
     * 매장의 주문 목록
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public List<OrderDto> orderList(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return detailsService.findAll(getUserId(userDetails));
    }


    /**
     * 관리자 매장별 주문 목록 확인
     *
     * @param storeId
     * @return
     */
    @GetMapping("/{storeId}")
    @PreAuthorize("hasAnyRole('MASTER', 'MANAGER')")
    public List<OrderDto> orderAdminList(@PathVariable("storeId") UUID storeId) {
        return detailsService.findAllByStoreId(storeId);
    }

    private UUID getUserId(UserDetailsImpl userDetails) {
        return userDetails.getUser().getId().getId();
    }
}

interface OrderRepository extends Repository<Order, UUID> {

}