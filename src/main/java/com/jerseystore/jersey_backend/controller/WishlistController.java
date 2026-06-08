package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.WishlistItemRequest;
import com.jerseystore.jersey_backend.dto.response.WishlistResponse;
import com.jerseystore.jersey_backend.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public WishlistResponse getMyWishlist() {
        return wishlistService.getMyWishlist();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public WishlistResponse addToWishlist(@Valid @RequestBody WishlistItemRequest request) {
        return wishlistService.addToWishlist(request);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{itemId}")
    public WishlistResponse removeFromWishlist(@PathVariable Long itemId) {
        return wishlistService.removeFromWishlist(itemId);
    }
}