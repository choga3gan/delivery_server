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
import com.choga3gan.delivery.order.event.PaymentCancelEvent;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    @Convert(converter = PriceConverter.class)
    private Price totalPrice;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="p_order_item", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name="order_item_idx")
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private OrderStatus orderStatus;

    private UUID userId;

    private String userName;

    private String userEmail;

    @Column(length = 100, nullable = false)
    private String userAddress;

    private boolean reviewed;

    @Builder
    public Order(UUID orderId,  UUID userId,
                 String userAddress, String userName, String userEmail, List<OrderItem> orderItems) { // List<OrderItem> 추가
        this.orderId = orderId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userAddress = userAddress;
        this.orderStatus = OrderStatus.ORDER_PENDING; // 주문 대기
        setOrderItems(orderItems);
        calculateTotalPrice();

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
            Events.trigger(new PaymentCancelEvent(orderId));
        }
    }

    /**
     * 주문서 수정
     *  - 주문 정보 변경은 주문접수, 입금확인으로 한정
     * @param userId
     * @param name
     * @param email
     * @param address
     */
    public void updateOrder(UUID userId, String name, String email, String address) {
        if (!List.of(OrderStatus.ORDER_ACCEPT, OrderStatus.CONFIRM_PAYMENT).contains(orderStatus)) {
            throw new OrderNotUpdatableException("주문 정보 변경은 주문접수, 입금확인 상태에서만 가능합니다.");
        };

        userName = name;
        userEmail = email;
        userAddress = address;
    }

    public void changeReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}