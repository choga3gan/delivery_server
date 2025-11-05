/**
 * @package
 * @class       OrderStatus
 * @description Order 테이블의 order_status 를 대신하여 OrderStatus 파일을 따로 만듦
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    // 주문 접수 이전/결제 대기
    ORDER_PENDING("결제 대기"),
    // 주문 접수 완료 (입금 확인 후)
    ORDER_ACCEPT("주문 접수"),
    // 배송 시작
    IN_DELIVERY("배송 중"),
    // 배송 완료
    DELIVERY_COMPLETED("배송 완료"),
    // 주문 취소 (입금 전)
    ORDER_CANCEL("주문 취소"),
    // 주문 환불 (입금 후 취소)
    ORDER_REFUND("주문 환불");

    private final String description;
}
/*package com.choga3gan.delivery.order.domain;

public enum OrderStatus {
    ORDER_ACCEPT, // 주문접수
    PAYMENT_CONFIRM, // 입금 확인
    PREPARING, // 배달 준비중
    DELIVERY, // 배달중
    DELIVERY_DONE, // 배달 완료
    ORDER_DONE, // 주문처리 완료
    ORDER_CANCEL, // 주문 취소(미입금)
    ORDER_REFUND, // 환불
    EXCHANGE, // 교환
}*/
