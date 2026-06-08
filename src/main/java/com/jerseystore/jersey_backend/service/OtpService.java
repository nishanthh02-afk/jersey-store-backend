package com.jerseystore.jersey_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final JavaMailSender mailSender;

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("JerseyStore — Password Reset OTP");
        message.setText(
                "Your password reset OTP is: " + otp + "\n\n" +
                        "Valid for 10 minutes.\n" +
                        "Do not share with anyone.\n\n" +
                        "Team JerseyStore"
        );
        mailSender.send(message);
    }
}