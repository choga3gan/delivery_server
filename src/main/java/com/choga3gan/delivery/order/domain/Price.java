/**
 * @package
 * @class       Price
 * @description 가격 변수 제어를 위한 가격 클래스 따로 생성
 *
 * @author      leehanbeen
 * @since
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 *  2025.11.04    leehanbeen       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable // JPA 임베디드 타입으로 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price implements Serializable {

    private int value; // 가격은 정수(원)로 관리

    public Price(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
        this.value = value;
    }

    public Price add(Price other) {
        return new Price(this.value + other.value);
    }

    public Price multiply(int quantity) {
        return new Price(this.value * quantity);
    }
}
