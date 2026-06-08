package com.jerseystore.jersey_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageRequest {

    private String imageUrl;
    private Boolean isPrimary;
}
