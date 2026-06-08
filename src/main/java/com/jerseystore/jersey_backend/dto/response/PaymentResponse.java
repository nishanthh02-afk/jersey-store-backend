package com.jerseystore.jersey_backend.dto.response;

import com.jerseystore.jersey_backend.enums.PaymentStatus;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private Double amount;
    private PaymentStatus status;
}