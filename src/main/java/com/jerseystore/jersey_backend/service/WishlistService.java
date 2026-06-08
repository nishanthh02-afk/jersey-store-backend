package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.WishlistItemRequest;
import com.jerseystore.jersey_backend.dto.response.WishlistItemResponse;
import com.jerseystore.jersey_backend.dto.response.WishlistResponse;
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
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Wishlist getOrCreateWishlist(User user) {
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setUser(user);
                    return wishlistRepository.save(wishlist);
                });
    }

    private WishlistItemResponse convertItemToResponse(WishlistItem item) {
        return new WishlistItemResponse(
                item.getId(),
                item.getProductVariant().getId(),
                item.getProductVariant().getProduct().getName(),
                item.getProductVariant().getKitType().name(),
                item.getProductVariant().getSize().name(),
                item.getProductVariant().getPrice()
        );
    }

    private WishlistResponse convertWishlistToResponse(Wishlist wishlist) {
        List<WishlistItemResponse> items = wishlistItemRepository.findByWishlist(wishlist)
                .stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());
        return new WishlistResponse(wishlist.getId(), items);
    }

    public WishlistResponse getMyWishlist() {
        User user = getCurrentUser();
        Wishlist wishlist = getOrCreateWishlist(user);
        return convertWishlistToResponse(wishlist);
    }

    @Transactional
    public WishlistResponse addToWishlist(WishlistItemRequest request) {
        User user = getCurrentUser();
        Wishlist wishlist = getOrCreateWishlist(user);

        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        wishlistItemRepository.findByWishlistAndProductVariant(wishlist, variant)
                .ifPresent(existing -> {
                    throw new RuntimeException("Item already in wishlist");
                });

        WishlistItem item = new WishlistItem();
        item.setWishlist(wishlist);
        item.setProductVariant(variant);
        wishlistItemRepository.save(item);

        return convertWishlistToResponse(wishlist);
    }

    public WishlistResponse removeFromWishlist(Long itemId) {
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
        Wishlist wishlist = item.getWishlist();
        wishlistItemRepository.deleteById(itemId);
        return convertWishlistToResponse(wishlist);
    }
}