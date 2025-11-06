package com.choga3gan.delivery.cart.domain;

import com.choga3gan.delivery.global.domain.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * p_cart 테이블에 매핑되는 엔티티
 * 주의: 이 수정은 CartItem.java에도 quantity 필드를 추가했다는 가정하에 이루어집니다.
 */
@Getter
@Entity
@Table(name = "p_cart")
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartId; // cart_id (PK)

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId; // user_id

    private Double quantity; // quantity (총 수량)
    private Double totalPrice; // total_price (총 금액)

    // p_cart_item (장바구니 항목)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "p_cart_item", joinColumns = @JoinColumn(name = "cart_id"))
    // JPA가 cart_item_idx 컬럼을 리스트 순서(인덱스) 관리용으로만 사용합니다.
    // CartItem 값 객체에서 cartItemIdx 필드를 반드시 제거해야 합니다.
    @OrderColumn(name = "cart_item_idx")
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Cart(UUID cartId, UUID userId, Double quantity, Double totalPrice, List<CartItem> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.quantity = quantity != null ? quantity : 0.0;
        this.totalPrice = totalPrice != null ? totalPrice : 0.0;
        if (cartItems != null) {
            this.cartItems = cartItems;
        }
        // 생성자에서는 외부에서 전달된 값으로 초기화하고, 총합을 재계산합니다.
        calculateTotals();
    }

    // --- 비즈니스 로직 ---

    /**
     * 장바구니에 새로운 항목을 추가합니다.
     */
    public void addCartItem(CartItem newItem) {
        // @OrderColumn이 인덱스를 관리하므로, 수동 인덱스 설정 로직(newItem.updateCartItemIdx)은 제거합니다.
        this.cartItems.add(newItem);
        calculateTotals();
    }

    /**
     * 장바구니 내 특정 항목의 수량을 업데이트합니다.
     * 주의: CartItem에 quantity 필드가 있다고 가정하고, 해당 필드를 찾아 수량을 업데이트해야 합니다.
     * @param targetIdx 수정할 항목의 인덱스 (리스트의 순서)
     * @param newQuantity 새로운 수량
     */
    public void updateItemQuantity(int targetIdx, double newQuantity) {
        if (targetIdx < 0 || targetIdx >= this.cartItems.size()) {
            throw new IllegalArgumentException("유효하지 않은 장바구니 항목 인덱스입니다.");
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다. 삭제는 DELETE API를 사용하세요.");
        }

        // 해당 인덱스의 CartItem을 찾아 수량을 업데이트합니다.
        CartItem itemToUpdate = this.cartItems.get(targetIdx);
        itemToUpdate.updateQuantity(newQuantity); // **CartItem에 updateQuantity() 메서드가 필요합니다.**

        calculateTotals();
    }

    /**
     * 장바구니 내 특정 상품 항목을 삭제합니다.
     * @param targetIdx 삭제할 항목의 인덱스
     */
    public void removeCartItem(int targetIdx) {
        if (targetIdx < 0 || targetIdx >= this.cartItems.size()) {
            throw new IllegalArgumentException("유효하지 않은 장바구니 항목 인덱스입니다.");
        }

        this.cartItems.remove(targetIdx);
        // @OrderColumn이 인덱스 재맵핑을 처리하므로, 수동 인덱스 재맵핑 로직은 제거합니다.

        calculateTotals();
    }

    /**
     * 장바구니 내 모든 항목을 비웁니다.
     */
    public void clearCart() {
        this.cartItems.clear();
        calculateTotals();
    }

    /**
     * 총 수량과 총 가격을 재계산합니다. (p_cart 스키마 필드 업데이트)
     */
    private void calculateTotals() {
        this.quantity = 0.0;
        this.totalPrice = 0.0;

        for (CartItem item : this.cartItems) {
            // CartItem에 quantity 필드가 있다고 가정하고 계산합니다.
            double itemQuantity = item.getQuantity(); // **CartItem에 getQuantity() 메서드가 필요합니다.**

            this.quantity += itemQuantity;
            this.totalPrice += item.getProductPrice() * itemQuantity; // 상품 가격 * 수량
        }
    }
}