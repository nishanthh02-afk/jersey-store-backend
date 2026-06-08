package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.CartItemRequest;
import com.jerseystore.jersey_backend.dto.response.CartResponse;
import com.jerseystore.jersey_backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public CartResponse getMyCart() {
        return cartService.getMyCart();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public CartResponse addToCart(@Valid @RequestBody CartItemRequest request) {
        return cartService.addToCart(request);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{itemId}")
    public CartResponse updateQuantity(@PathVariable Long itemId,
                                       @RequestParam Integer quantity) {
        return cartService.updateQuantity(itemId, quantity);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{itemId}")
    public CartResponse removeFromCart(@PathVariable Long itemId) {
        return cartService.removeFromCart(itemId);
    }
}