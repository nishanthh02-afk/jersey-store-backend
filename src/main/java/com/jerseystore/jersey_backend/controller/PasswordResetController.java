package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.ForgotPasswordRequest;
import com.jerseystore.jersey_backend.dto.request.VerifyOtpRequest;
import com.jerseystore.jersey_backend.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return passwordResetService.forgotPassword(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @RequestBody VerifyOtpRequest request) {
        return passwordResetService.resetPassword(request);
    }
}