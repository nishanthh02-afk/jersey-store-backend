package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.response.UserResponse;
import com.jerseystore.jersey_backend.entity.Order;
import com.jerseystore.jersey_backend.enums.OrderStatus;
import com.jerseystore.jersey_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return adminService.getAllOrders();
    }

    @PutMapping("/orders/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id,
                                   @RequestParam OrderStatus status) {
        return adminService.updateOrderStatus(id, status);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return Map.of(
                "totalUsers", adminService.getTotalUsers(),
                "totalOrders", adminService.getTotalOrders(),
                "totalProducts", adminService.getTotalProducts(),
                "totalRevenue", adminService.getTotalRevenue()
        );
    }
}