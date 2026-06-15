package com.jerseystore.jersey_backend.dto.response;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class WishlistItemResponse {
    private Long id;
    private Long productId;
    private Long productVariantId;
    private String productName;
    private String league;
    private String kitType;
    private String size;
    private Double price;
    private String imageUrl;
}