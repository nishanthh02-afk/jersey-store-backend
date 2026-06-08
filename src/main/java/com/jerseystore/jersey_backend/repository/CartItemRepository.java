package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Cart;
import com.jerseystore.jersey_backend.entity.CartItem;
import com.jerseystore.jersey_backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);
}