package com.jerseystore.jersey_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    private void sendEmail(String to, String subject, String body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            Map<String, Object> payload = new HashMap<>();
            payload.put("sender", Map.of("name", "Realwear", "email", "nishanthh02@gmail.com"));
            payload.put("to", List.of(Map.of("email", to)));
            payload.put("subject", subject);
            payload.put("textContent", body);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(BREVO_API_URL, request, String.class);
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }
    }

    public void sendOrderConfirmation(String to, String customerName,
                                      Long orderId, Double totalAmount) {
        sendEmail(
                to,
                "Order Confirmed — Realwear #" + orderId,
                "Dear " + customerName + ",\n\n" +
                        "Your order #" + orderId + " has been placed successfully.\n\n" +
                        "Total Amount: ₹" + totalAmount + "\n\n" +
                        "We will process your order shortly.\n\n" +
                        "Thank you for shopping with Realwear!\n\nTeam Realwear"
        );
    }

    public void sendOrderCancellation(String to, String customerName, Long orderId) {
        sendEmail(
                to,
                "Order Cancelled — Realwear #" + orderId,
                "Dear " + customerName + ",\n\n" +
                        "Your order #" + orderId + " has been cancelled.\n\n" +
                        "Thank you,\nTeam Realwear"
        );
    }

    public void sendOrderStatusUpdate(String to, String customerName,
                                      Long orderId, String status) {
        sendEmail(
                to,
                "Order Update — Realwear #" + orderId,
                "Dear " + customerName + ",\n\n" +
                        "Your order #" + orderId + " status: " + status + "\n\n" +
                        "Thank you,\nTeam Realwear"
        );
    }
}