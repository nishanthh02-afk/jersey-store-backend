package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Product;
import com.jerseystore.jersey_backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {


    List<ProductVariant> findByProduct(Product product);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductVariant p WHERE p.id = :id")
    Optional<ProductVariant> findByIdWithLock(@Param("id") Long id);

}
