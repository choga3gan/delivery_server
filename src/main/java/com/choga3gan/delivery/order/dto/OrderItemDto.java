package com.choga3gan.delivery.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemDto {
    private UUID itemId;
    private int quantity;
}