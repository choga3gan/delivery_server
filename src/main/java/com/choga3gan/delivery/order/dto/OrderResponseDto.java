package com.choga3gan.delivery.order.dto;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class OrderResponseDto {

    private UUID orderId;
    private UUID userId;
    private UUID storeId;
    private String userAddress;
    private String totalPrice; // 문자열로 포맷팅하여 전달
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> items;

    public static OrderResponseDto from(Order order) {
        List<OrderItemResponseDto> items = order.getOrderItems().stream()
                .map(item -> OrderItemResponseDto.builder()
                        .itemId(item.getItemId())
                        .quantity(item.getQuantity())
                        .itemPrice(String.valueOf(item.getItemPrice().getValue()))
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .userId(order.getUsedId())
                .storeId(order.getStoreId())
                .userAddress(order.getUserAddress())
                .totalPrice(String.valueOf(order.getTotalPrice().getValue()))
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getCreatedAt())
                .items(items)
                .build();
    }
}

@Getter
@Builder
class OrderItemResponseDto {
    private UUID itemId;
    private int quantity;
    private String itemPrice;
}

/*import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderStatus;
import com.choga3gan.delivery.order.domain.Price;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderResponseDto {
    private final UUID orderId;
    private final Double quantity;
    private final Price totalPrice;
    private final OrderStatus orderStatus;
    private final UUID usedId;
    private final String userAddress;
    private final UUID storeId;
    private final boolean reviewed;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getOrderStatus();
        this.usedId = order.getUsedId();
        this.userAddress = order.getUserAddress();
        this.storeId = order.getStoreId();
        this.reviewed = order.isReviewed();
    }
}*/
