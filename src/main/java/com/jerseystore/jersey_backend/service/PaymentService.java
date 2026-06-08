package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.PaymentRequest;
import com.jerseystore.jersey_backend.dto.request.PaymentVerifyRequest;
import com.jerseystore.jersey_backend.dto.response.PaymentResponse;
import com.jerseystore.jersey_backend.entity.Order;
import com.jerseystore.jersey_backend.entity.Payment;
import com.jerseystore.jersey_backend.enums.OrderStatus;
import com.jerseystore.jersey_backend.enums.PaymentStatus;
import com.jerseystore.jersey_backend.repository.OrderRepository;
import com.jerseystore.jersey_backend.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private PaymentResponse convertToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getRazorpayOrderId(),
                payment.getRazorpayPaymentId(),
                payment.getAmount(),
                payment.getStatus()
        );
    }

    public PaymentResponse createPayment(PaymentRequest request) throws RazorpayException {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        paymentRepository.findByOrder(order).ifPresent(p -> {
            throw new RuntimeException("Payment already created for this order");
        });

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(order.getTotalAmount() * 100));
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_" + order.getId());

        com.razorpay.Order razorpayOrder = client.orders.create(orderRequest);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setRazorpayOrderId(razorpayOrder.get("id"));
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);

        paymentRepository.save(payment);
        return convertToResponse(payment);
    }

    public PaymentResponse verifyPayment(PaymentVerifyRequest request) {

        Payment payment = paymentRepository.findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        try {
            String data = request.getRazorpayOrderId() + "|" + request.getRazorpayPaymentId();

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    keySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            String generatedSignature = hexString.toString();

            if (generatedSignature.equals(request.getRazorpaySignature())) {
                payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
                payment.setStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(payment);

                Order order = payment.getOrder();
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                throw new RuntimeException("Payment verification failed");
            }

        } catch (Exception e) {
            throw new RuntimeException("Payment verification error: " + e.getMessage());
        }

        return convertToResponse(payment);
    }
}