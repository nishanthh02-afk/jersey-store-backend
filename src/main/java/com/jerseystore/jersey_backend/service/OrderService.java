package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.PlaceOrderRequest;
import com.jerseystore.jersey_backend.dto.response.OrderItemResponse;
import com.jerseystore.jersey_backend.dto.response.OrderResponse;
import com.jerseystore.jersey_backend.entity.*;
import com.jerseystore.jersey_backend.enums.OrderStatus;
import com.jerseystore.jersey_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final EmailService emailService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private OrderItemResponse convertItemToResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProductVariant().getProduct().getName(),
                item.getProductVariant().getKitType().name(),
                item.getProductVariant().getSize().name(),
                item.getQuantity(),
                item.getPrice(),
                item.getPrice() * item.getQuantity()
        );
    }

    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> items = orderItemRepository.findByOrder(order)
                .stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());

        String deliveryAddress = order.getAddress().getDoorNumber() + ", " +
                order.getAddress().getStreet() + ", " +
                order.getAddress().getCity() + ", " +
                order.getAddress().getState() + " - " +
                order.getAddress().getPincode();

        return new OrderResponse(
                order.getId(),
                order.getUser().getName(),
                deliveryAddress,
                items,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request) {
        User user = getCurrentUser();

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(0.0);

        orderRepository.save(order);

        double totalAmount = 0;

        for (var itemRequest : request.getItems()) {
            ProductVariant variant = productVariantRepository.findByIdWithLock(
                            itemRequest.getProductVariantId())
                    .orElseThrow(() -> new RuntimeException("Product variant not found"));

            if (variant.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " +
                        variant.getProduct().getName());
            }

            variant.setStock(variant.getStock() - itemRequest.getQuantity());
            productVariantRepository.save(variant);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductVariant(variant);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(variant.getPrice());
            totalAmount += variant.getPrice() * itemRequest.getQuantity();
            orderItemRepository.save(orderItem);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        return convertToResponse(order);
    }

    public List<OrderResponse> getMyOrders() {
        User user = getCurrentUser();
        return orderRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponse(order);
    }

    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }

        orderItemRepository.findByOrder(order).forEach(item -> {
            ProductVariant variant = item.getProductVariant();
            variant.setStock(variant.getStock() + item.getQuantity());
            productVariantRepository.save(variant);
        });

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        try {
            emailService.sendOrderCancellation(
                    order.getUser().getEmail(),
                    order.getUser().getName(),
                    order.getId()
            );
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }
        return convertToResponse(order);
    }
}