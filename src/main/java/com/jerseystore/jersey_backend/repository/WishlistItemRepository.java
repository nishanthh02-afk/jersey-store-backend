package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.ProductVariant;
import com.jerseystore.jersey_backend.entity.Wishlist;
import com.jerseystore.jersey_backend.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByWishlist(Wishlist wishlist);
    Optional<WishlistItem> findByWishlistAndProductVariant(Wishlist wishlist, ProductVariant productVariant);
}