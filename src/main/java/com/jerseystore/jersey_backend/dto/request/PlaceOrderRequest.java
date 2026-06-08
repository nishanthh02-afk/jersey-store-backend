package com.jerseystore.jersey_backend.dto.request;

import lombok.*;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PlaceOrderRequest {
    private Long addressId;
    private List<OrderItemRequest> items;
}