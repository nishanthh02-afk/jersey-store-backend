package com.jerseystore.jersey_backend.repository;

import com.jerseystore.jersey_backend.entity.Address;
import com.jerseystore.jersey_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
    Optional<Address> findByUserAndIsDefault(User user, Boolean isDefault);
}