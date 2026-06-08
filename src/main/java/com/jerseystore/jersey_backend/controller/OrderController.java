package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.PlaceOrderRequest;
import com.jerseystore.jersey_backend.dto.response.OrderResponse;
import com.jerseystore.jersey_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public OrderResponse placeOrder(@RequestBody PlaceOrderRequest request) {
        return orderService.placeOrder(request);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
}