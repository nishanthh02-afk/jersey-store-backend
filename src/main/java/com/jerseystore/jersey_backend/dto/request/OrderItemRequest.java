package com.jerseystore.jersey_backend.dto.request;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderItemRequest {
    private Long productVariantId;
    private Integer quantity;
}