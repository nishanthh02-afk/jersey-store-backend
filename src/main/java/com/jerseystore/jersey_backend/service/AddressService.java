package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.AddressRequest;
import com.jerseystore.jersey_backend.dto.response.AddressResponse;
import com.jerseystore.jersey_backend.entity.Address;
import com.jerseystore.jersey_backend.entity.User;
import com.jerseystore.jersey_backend.repository.AddressRepository;
import com.jerseystore.jersey_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private AddressResponse convertToResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getUser().getId(),
                address.getDoorNumber(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getCountry(),
                address.getIsDefault()
        );
    }

    public AddressResponse addAddress(AddressRequest request) {
        User user = getCurrentUser();

        Address address = new Address();
        address.setUser(user);
        address.setDoorNumber(request.getDoorNumber());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault() != null ? request.getIsDefault() : false);

        addressRepository.save(address);
        return convertToResponse(address);
    }

    public List<AddressResponse> getMyAddresses() {
        User user = getCurrentUser();
        return addressRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AddressResponse updateAddress(Long id, AddressRequest request) {
        User user = getCurrentUser();
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setDoorNumber(request.getDoorNumber());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setCountry(request.getCountry());

        addressRepository.save(address);
        return convertToResponse(address);
    }

    public String deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressRepository.deleteById(id);
        return "Address deleted successfully";
    }

    @Transactional
    public AddressResponse setDefaultAddress(Long id) {
        User user = getCurrentUser();

        addressRepository.findByUserAndIsDefault(user, true)
                .ifPresent(existing -> {
                    existing.setIsDefault(false);
                    addressRepository.save(existing);
                });

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.setIsDefault(true);
        addressRepository.save(address);
        return convertToResponse(address);
    }
}