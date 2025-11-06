package com.choga3gan.delivery.order.service;

import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderItem;
import com.choga3gan.delivery.order.domain.Price;
import com.choga3gan.delivery.order.domain.RoleCheck;
import com.choga3gan.delivery.order.dto.OrderItemRequest;
import com.choga3gan.delivery.order.dto.OrderRequest;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.product.domain.Product;
import com.choga3gan.delivery.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RoleCheck roleCheck;

    /**
     * 주문 등록
     *
     * @param userId
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UUID createOrder(UUID userId, OrderRequest req) {

        Order order = Order.builder()
                .userId(userId)
                .userName(req.name())
                .userAddress(req.address())
                .userEmail(req.email())
                .orderItems(toOrderItems(req.items()))
                .build();

        orderRepository.save(order);

        return order.getOrderId();
    }

    /**
     * 주문서 수정
     *
     * @param name
     * @param email
     * @param address
     */
    @Transactional
    public void updateOrder(UUID userId, UUID orderId, String name, String email, String address) {
        roleCheck.check(orderId);
        Order order = getOrder(orderId);

        order.updateOrder(userId, name, email, address);
    }

    /**
     * 주문 취소
     *
     * @param orderId
     */
    @Transactional
    public void cancelOrder(UUID orderId) {
        roleCheck.check(orderId);
        Order order = getOrder(orderId);
        order.cancel();
    }


    @Transactional
    public void acceptOrder(UUID orderId, boolean accept) {
        roleCheck.check(orderId);
        Order order = getOrder(orderId);

        if (accept) order.orderAccept();
        else order.cancel(); // 취소
    }



    private List<OrderItem> toOrderItems(List<OrderItemRequest> items) {
        List<UUID> productIds = items.stream().map(OrderItemRequest::productId).toList();
        Map<UUID, Product> products = productRepository.findAllById(productIds).stream().collect(Collectors.toMap(Product::getProductId, i -> i, (i1, i2) -> i2));

       return items.stream()
                .map(i -> {
                    Product product = products.get(i.productId());
                   return OrderItem.builder()
                           .productId(i.productId())
                           .storeId(product.getStore().getStoreId())
                           .quantity(i.quantity())
                           .productPrice(new Price(product.getPrice()))
                           .productName(product.getProductName())
                           .build();

                }).toList();

    }

    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }
}
