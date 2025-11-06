package com.choga3gan.delivery.payment.repository;

import com.choga3gan.delivery.payment.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Page<Payment> findByUserId(UUID userId, Pageable pageable);
    Page<Payment> findByStoreId(UUID storeId, Pageable pageable);
    Optional<Payment> findByPaymentId(UUID paymentId);
}
