package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByTeam(String team);
    List<Product> findByLeague(String league);
    List<Product> findByBrand(String brand);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByTeamContainingIgnoreCase(String team);
    List<Product> findByLeagueContainingIgnoreCase(String league);
    List<Product> findByBrandContainingIgnoreCase(String brand);

}
