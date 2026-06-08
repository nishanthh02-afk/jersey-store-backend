package com.jerseystore.jersey_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOrderConfirmation(String to, String customerName,
                                      Long orderId, Double totalAmount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Order Confirmed — JerseyStore #" + orderId);
        message.setText(
                "Dear " + customerName + ",\n\n" +
                        "Your order #" + orderId + " has been placed successfully.\n\n" +
                        "Total Amount: ₹" + totalAmount + "\n\n" +
                        "We will process your order shortly.\n\n" +
                        "Thank you for shopping with JerseyStore!\n\n" +
                        "Team JerseyStore"
        );
        mailSender.send(message);
    }

    public void sendOrderCancellation(String to, String customerName, Long orderId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Order Cancelled — JerseyStore #" + orderId);
        message.setText(
                "Dear " + customerName + ",\n\n" +
                        "Your order #" + orderId + " has been cancelled successfully.\n\n" +
                        "If you did not cancel this order, please contact us immediately.\n\n" +
                        "Thank you,\nTeam JerseyStore"
        );
        mailSender.send(message);
    }

    public void sendOrderStatusUpdate(String to, String customerName,
                                      Long orderId, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Order Status Update — JerseyStore #" + orderId);
        message.setText(
                "Dear " + customerName + ",\n\n" +
                        "Your order #" + orderId + " status has been updated to: " + status + "\n\n" +
                        "Thank you for shopping with JerseyStore!\n\n" +
                        "Team JerseyStore"
        );
        mailSender.send(message);
    }
}