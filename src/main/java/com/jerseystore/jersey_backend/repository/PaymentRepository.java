package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Order;
import com.jerseystore.jersey_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}