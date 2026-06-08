package com.jerseystore.jersey_backend.dto.response;

import com.jerseystore.jersey_backend.enums.OrderStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderResponse {
    private Long id;
    private String customerName;
    private String deliveryAddress;
    private List<OrderItemResponse> items;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
}