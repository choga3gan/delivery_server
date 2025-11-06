/**
 * @package
 * @class       OrderItem
 * @description 주문한 아이템 목록
 *
 * @author      leehanbeen
 * @since
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025.11.04    leehanbeen       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable // @ElementCollection으로 사용되므로 임베디드 타입으로 선언
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem implements Serializable {

    private UUID productId;
    private String productName;
    private UUID storeId;
    private int quantity; // 수량
    private Price productPrice; // 개당 가격 (임시로 추가. 실제는 상품 서비스에서 가져와야 함)

    // @Builder 대신 간단한 생성자 사용
    @Builder
    public OrderItem(UUID productId, UUID storeId, int quantity, Price productPrice, String productName) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 1개 이상이어야 합니다.");
        }
        this.productId = productId;
        this.storeId = storeId;
        this.productName = productName;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public Price getTotalPrice() {
        // 상품 가격 * 수량
        return productPrice.multiply(quantity);
    }
}
