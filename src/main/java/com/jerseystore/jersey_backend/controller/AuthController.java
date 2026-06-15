package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.ForgotPasswordRequest;
import com.jerseystore.jersey_backend.dto.request.LoginRequest;
import com.jerseystore.jersey_backend.dto.request.RegisterRequest;
import com.jerseystore.jersey_backend.dto.request.VerifyOtpRequest;
import com.jerseystore.jersey_backend.dto.response.AuthResponse;
import com.jerseystore.jersey_backend.service.AuthService;
import com.jerseystore.jersey_backend.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;  // ADD THIS

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return passwordResetService.forgotPassword(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @RequestBody VerifyOtpRequest request) {
        return passwordResetService.resetPassword(request);
    }

}
