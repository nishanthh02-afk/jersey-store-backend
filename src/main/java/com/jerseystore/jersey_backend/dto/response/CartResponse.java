package com.jerseystore.jersey_backend.dto.response;

import lombok.*;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CartResponse {
    private Long id;
    private List<CartItemResponse> items;
    private Double grandTotal;
}