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
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable // @ElementCollection으로 사용되므로 임베디드 타입으로 선언
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem implements Serializable {

    private UUID itemId;
    private int quantity; // 수량
    private Price itemPrice; // 개당 가격 (임시로 추가. 실제는 상품 서비스에서 가져와야 함)

    // @Builder 대신 간단한 생성자 사용
    public OrderItem(UUID itemId, int quantity, Price itemPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 1개 이상이어야 합니다.");
        }
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public Price getTotalPrice() {
        // 상품 가격 * 수량
        return itemPrice.multiply(quantity);
    }
}
/*package com.choga3gan.delivery.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;


*//**
 * 선택한 옵션
 *  옵션명_금액_수량//옵션명_금액_수량...
 *//*
@ToString
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    private UUID productId;

    @Column(length = 100, nullable = false)
    private String productName;

    @Convert(converter = PriceConverter.class)
    private Price productPrice;

    @Builder
    public OrderItem(UUID productId, String productName, Price productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    *//*private Price calculateTotalPrice() {
        int optionPrice = options == null ? 0 : options.stream()
                .mapToInt(o -> o.getTotalPrice().getValue()).sum();

        return price.multiply(quantity).add(new Price(optionPrice));
    }*//*
}*/
