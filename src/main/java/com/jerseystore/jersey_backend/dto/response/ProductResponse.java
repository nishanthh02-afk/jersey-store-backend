package com.jerseystore.jersey_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String team;
    private String league;
    private String brand;
    private List<ProductVariantResponse> variants;
    private List<ProductImageResponse> images;
}
