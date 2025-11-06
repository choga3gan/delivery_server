/**
 * @package     com/choga3gan/delivery/order/domain
 * @class       Order
 * @description Order 도메인 클래스
 *
 * @author      leehanbeen
 * @since
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 *  2025.11.04   leehanbeen       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.order.domain;


import com.choga3gan.delivery.global.domain.Auditable;
import com.choga3gan.delivery.global.event.Events;
import com.choga3gan.delivery.order.event.OrderAcceptEvent;
import com.choga3gan.delivery.order.event.OrderRefundEvent;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


/**
 *  1. 주문 상품이 1개 이상이어야 주문이 가능
 *  2. 주문 상품의 총 금액은 주문상품 목록을 통해서만 계산된다.
 *  3. 주문 취소는 주문 접수 후 5분 이내 가능
 *      - 입금 확인 전 : 주문 취소 상태(ORDER_CANCEL)
 *      - 입금 완료 후 : 주문 환불 상태(ORDER_REFUND) / 결제 취소 진행(이벤트 발생)
 *  4. 배송중 주문 상태는 입금 확인이 되어야만 변경 가능
 */
@ToString
@Entity
@Table(name = "p_order")
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private Double quantity;

    @Convert(converter = PriceConverter.class)
    private Price totalPrice;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="p_order_item", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name="order_item_idx")
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private OrderStatus orderStatus;

    private UUID usedId;

    @Column(length = 100, nullable = false)
    private String userAddress;

    private UUID storeId;

    private boolean reviewed;

    @Builder
    public Order(UUID orderId, double quantity, Price totalPrice, OrderStatus orderStatus, UUID usedId,
                 String userAddress, UUID storeId, boolean reviewed, List<OrderItem> orderItems) { // List<OrderItem> 추가
        this.orderId = orderId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.usedId = usedId;
        this.userAddress = userAddress;
        this.storeId = storeId;
        this.reviewed = false;
        this.orderItems = orderItems; // 필드에 값 대입 추가
    }


    private void setOrderItems(List<OrderItem> orderItems) {
        if(orderItems == null || orderItems.isEmpty()) {
            return;
        }

        this.orderItems = orderItems;
    }

    private void calculateTotalPrice() {
        this.totalPrice = new Price(orderItems.stream().mapToInt(x -> x.getTotalPrice().getValue()).sum());
    }

    //주문 접수
   public void orderAccept() {
        this.orderStatus = OrderStatus.ORDER_ACCEPT;

        //주문 접수 후 이벤트 발생 시키기
        Events.trigger(new OrderAcceptEvent(orderId));
    }

    // 배송 상태로 변경
    public void delivery() {
        if (orderStatus != OrderStatus.ORDER_ACCEPT) return;

        this.orderStatus = OrderStatus.IN_DELIVERY;
    }

    // 배송 완료
    public void complete() {
        if (orderStatus != OrderStatus.IN_DELIVERY) return;

        this.orderStatus = OrderStatus.DELIVERY_COMPLETED;
    }

    //주문 취소
    public void cancel() {
        //입금 확인 전이면 주문 취소, 입금 후 주문 접수 후 5분 이내인 상태라면 환불
        if (this.orderStatus == OrderStatus.ORDER_ACCEPT) {
            this.orderStatus = OrderStatus.ORDER_CANCEL;
        } else if (createdAt != null && createdAt.isBefore(createdAt.plusMinutes(5L))) {
            this.orderStatus = OrderStatus.ORDER_REFUND;

            //결제 취소 요청
            Events.trigger(new OrderRefundEvent(orderId));
        }
    }
}