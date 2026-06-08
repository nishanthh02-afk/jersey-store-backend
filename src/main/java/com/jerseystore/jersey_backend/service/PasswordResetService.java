package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.ForgotPasswordRequest;
import com.jerseystore.jersey_backend.dto.request.VerifyOtpRequest;
import com.jerseystore.jersey_backend.entity.User;
import com.jerseystore.jersey_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    public String forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No account found with this email"));

        String otp = otpService.generateOtp();

        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        otpService.sendOtp(request.getEmail(), otp);

        return "OTP sent successfully to " + request.getEmail();
    }

    public String resetPassword(VerifyOtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No account found with this email"));

        if (user.getOtpCode() == null || !user.getOtpCode().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired — request a new one");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtpCode(null);
        user.setOtpExpiry(null);
        userRepository.save(user);

        return "Password reset successfully";
    }
}