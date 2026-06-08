package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.PaymentRequest;
import com.jerseystore.jersey_backend.dto.request.PaymentVerifyRequest;
import com.jerseystore.jersey_backend.dto.response.PaymentResponse;
import com.jerseystore.jersey_backend.service.PaymentService;
import com.razorpay.RazorpayException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public PaymentResponse createPayment(@Valid @RequestBody PaymentRequest request) throws RazorpayException {
        return paymentService.createPayment(request);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/verify")
    public PaymentResponse verifyPayment(@Valid @RequestBody PaymentVerifyRequest request) {
        return paymentService.verifyPayment(request);
    }
}