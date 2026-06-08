package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Order;
import com.jerseystore.jersey_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}