package com.jerseystore.jersey_backend.dto.response;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderItemResponse {
    private Long id;
    private String productName;
    private String kitType;
    private String size;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
}