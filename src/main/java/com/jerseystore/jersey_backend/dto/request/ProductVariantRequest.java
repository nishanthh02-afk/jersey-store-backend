package com.jerseystore.jersey_backend.dto.request;

import com.jerseystore.jersey_backend.enums.KitType;
import com.jerseystore.jersey_backend.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantRequest {

    private Long productId;
    private KitType kitType;
    private Size size;
    private Integer stock;
    private Double price;
}
