package com.jerseystore.jersey_backend.dto.response;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class WishlistItemResponse {
    private Long id;
    private Long productVariantId;
    private String productName;
    private String kitType;
    private String size;
    private Double price;
}