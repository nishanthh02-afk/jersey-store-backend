package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.response.OrderResponse;
import com.jerseystore.jersey_backend.dto.response.UserResponse;
import com.jerseystore.jersey_backend.entity.Order;
import com.jerseystore.jersey_backend.entity.User;
import com.jerseystore.jersey_backend.enums.OrderStatus;
import com.jerseystore.jersey_backend.enums.Role;
import com.jerseystore.jersey_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .role(user.getRole().name())
                        .createdAt(user.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // prevent admin deletion
        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Cannot delete admin account");
        }

        orderRepository.findByUser(user).forEach(order -> {
            paymentRepository.findByOrder(order).ifPresent(payment ->
                    paymentRepository.delete(payment));
            orderItemRepository.deleteAll(orderItemRepository.findByOrder(order));
            orderRepository.delete(order);
        });

        cartRepository.findByUser(user).ifPresent(cart -> {
            cartItemRepository.deleteAll(cartItemRepository.findByCart(cart));
            cartRepository.delete(cart);
        });

        wishlistRepository.findByUser(user).ifPresent(wishlist -> {
            wishlistItemRepository.deleteAll(wishlistItemRepository.findByWishlist(wishlist));
            wishlistRepository.delete(wishlist);
        });


        addressRepository.deleteAll(addressRepository.findByUser(user));

        userRepository.delete(user);
        return "User deleted successfully";
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> orderService.getOrderById(order.getId()))
                .collect(Collectors.toList());
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }

    public Long getTotalUsers() {
        return userRepository.count();
    }

    public Long getTotalOrders() {
        return orderRepository.count();
    }

    public Long getTotalProducts() {
        return productRepository.count();
    }

    public Double getTotalRevenue() {
        return orderRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }
}