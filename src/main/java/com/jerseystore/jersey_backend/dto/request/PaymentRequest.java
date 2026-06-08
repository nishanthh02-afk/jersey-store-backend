package com.jerseystore.jersey_backend.dto.request;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PaymentRequest {
    private Long orderId;
}