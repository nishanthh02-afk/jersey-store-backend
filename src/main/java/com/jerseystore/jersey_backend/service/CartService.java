package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.CartItemRequest;
import com.jerseystore.jersey_backend.dto.response.CartItemResponse;
import com.jerseystore.jersey_backend.dto.response.CartResponse;
import com.jerseystore.jersey_backend.entity.*;
import com.jerseystore.jersey_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    private CartItemResponse convertItemToResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProductVariant().getId(),
                item.getProductVariant().getProduct().getName(),
                item.getProductVariant().getKitType().name(),
                item.getProductVariant().getSize().name(),
                item.getProductVariant().getPrice(),
                item.getQuantity(),
                item.getProductVariant().getPrice() * item.getQuantity()
        );
    }

    private CartResponse convertCartToResponse(Cart cart) {
        List<CartItemResponse> items = cartItemRepository.findByCart(cart)
                .stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());

        Double grandTotal = items.stream()
                .mapToDouble(CartItemResponse::getTotalPrice)
                .sum();

        return new CartResponse(cart.getId(), items, grandTotal);
    }

    public CartResponse getMyCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        return convertCartToResponse(cart);
    }

    @Transactional
    public CartResponse addToCart(CartItemRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        if (variant.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        cartItemRepository.findByCartAndProductVariant(cart, variant)
                .ifPresentOrElse(
                        existing -> {
                            existing.setQuantity(existing.getQuantity() + request.getQuantity());
                            cartItemRepository.save(existing);
                        },
                        () -> {
                            CartItem item = new CartItem();
                            item.setCart(cart);
                            item.setProductVariant(variant);
                            item.setQuantity(request.getQuantity());
                            cartItemRepository.save(item);
                        }
                );

        return convertCartToResponse(cart);
    }

    public CartResponse updateQuantity(Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return convertCartToResponse(item.getCart());
    }

    public CartResponse removeFromCart(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        Cart cart = item.getCart();
        cartItemRepository.deleteById(itemId);
        return convertCartToResponse(cart);
    }

    public String clearCart(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
        return "Cart cleared";
    }
}