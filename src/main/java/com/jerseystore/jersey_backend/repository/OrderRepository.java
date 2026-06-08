package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Order;
import com.jerseystore.jersey_backend.entity.User;
import com.jerseystore.jersey_backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserAndStatus(User user, OrderStatus status);
}