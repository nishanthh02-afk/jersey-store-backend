package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.User;
import com.jerseystore.jersey_backend.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);
}