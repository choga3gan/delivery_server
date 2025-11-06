package com.choga3gan.delivery.payment.domain;

import com.choga3gan.delivery.global.domain.Auditable;
import com.choga3gan.delivery.order.domain.Price;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "p_payment")
@Getter
@NoArgsConstructor
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;
    private UUID userId;
    private UUID storeId;
    private UUID orderId;
    private Price totalPrice;
    private int quantity;

    @Builder
    public Payment(UUID userId, UUID storeId, UUID orderId, Price totalPrice, int quantity) {
        this.userId = userId;
        this.storeId = storeId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }
}
