package com.choga3gan.delivery.cart.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem implements Serializable {

    // private Integer cartItemIdx; <--- 에러 방지를 위해 제거

    private UUID productId;
    private String productName;
    private Double productPrice;
    private String productOptions;

    // 추가: 상품별 수량 관리를 위해 quantity 필드를 추가합니다.
    private Double quantity;

    @Builder
    public CartItem(UUID productId, String productName, Double productPrice, String productOptions, Double quantity) {
        if (productPrice == null || productPrice <= 0 || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("상품 가격과 수량은 0보다 커야 합니다.");
        }

        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOptions = productOptions;
        this.quantity = quantity; // 수량 초기화
    }

    /**
     * 수량 업데이트 (장바구니 서비스에서 호출)
     */
    public void updateQuantity(Double newQuantity) {
        this.quantity = newQuantity;
    }

    // 인덱스 관련 메서드와 필드는 모두 삭제했습니다.
}
