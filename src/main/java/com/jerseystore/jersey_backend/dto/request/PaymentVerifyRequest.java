package com.jerseystore.jersey_backend.dto.request;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PaymentVerifyRequest {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}