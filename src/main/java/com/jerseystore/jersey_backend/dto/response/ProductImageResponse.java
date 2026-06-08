package com.jerseystore.jersey_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageResponse {

    private Long id;
    private Long productId;
    private String imageUrl;
    private Boolean isPrimary;
}
