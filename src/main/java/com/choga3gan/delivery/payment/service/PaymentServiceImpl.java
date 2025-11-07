package com.choga3gan.delivery.payment.service;

import com.choga3gan.delivery.global.utils.service.SecurityUtilService;
import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderItem;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.order.service.OrderNotFoundException;
import com.choga3gan.delivery.payment.domain.Payment;
import com.choga3gan.delivery.payment.exception.PaymentNotCancelException;
import com.choga3gan.delivery.payment.exception.PaymentNotFoundException;
import com.choga3gan.delivery.payment.repository.PaymentRepository;
import com.choga3gan.delivery.store.service.CheckStoreAccess;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final SecurityUtilService securityUtilService;
    private final UserRepository userRepository;

    @Override
    public Payment createPayment(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        int quantity = order.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum();

        Payment payment = Payment.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .storeId(order.getOrderItems().getFirst().getStoreId())
                .totalPrice(order.getTotalPrice())
                .quantity(quantity)
                .build();
        order.delivery();
        return paymentRepository.save(payment);
    }

    @Override
    public Page<Payment> getPaymentFromUser() {
        User user = userRepository.findByUsername(securityUtilService.getCurrentUsername())
                .orElseThrow(UserNotFoundException::new);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return paymentRepository.findByUserId(user.getId().getId(), pageable);
    }

    @Override
    @CheckStoreAccess
    public Page<Payment> getPaymentFromStore(UUID storeId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return paymentRepository.findByStoreId(storeId, pageable);
    }

    @Override
    public void cancelPayment(UUID paymentId) {
        User user = userRepository.findByUsername(securityUtilService.getCurrentUsername())
                .orElseThrow(UserNotFoundException::new);
        Payment payment = paymentRepository.findByPaymentId(paymentId).orElseThrow(PaymentNotFoundException::new);
        if (!payment.getUserId().equals(user.getId().getId())) {
            throw new PaymentNotCancelException();
        }
        payment.softDelete(securityUtilService.getCurrentUsername());
    }
}
