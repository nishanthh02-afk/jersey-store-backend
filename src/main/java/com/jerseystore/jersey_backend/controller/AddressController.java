package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.AddressRequest;
import com.jerseystore.jersey_backend.dto.response.AddressResponse;
import com.jerseystore.jersey_backend.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public AddressResponse addAddress(@Valid @RequestBody AddressRequest request) {
        return addressService.addAddress(request);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public List<AddressResponse> getMyAddresses() {
        return addressService.getMyAddresses();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}")
    public AddressResponse updateAddress(@PathVariable Long id,
                                         @Valid @RequestBody AddressRequest request) {
        return addressService.updateAddress(id, request);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}/default")
    public AddressResponse setDefaultAddress(@PathVariable Long id) {
        return addressService.setDefaultAddress(id);
    }
}