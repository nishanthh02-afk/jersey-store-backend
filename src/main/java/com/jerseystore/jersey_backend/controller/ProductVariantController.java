package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.ProductVariantRequest;
import com.jerseystore.jersey_backend.dto.response.ProductVariantResponse;
import com.jerseystore.jersey_backend.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductVariantResponse addVariants(@Valid @RequestBody ProductVariantRequest request){
        return productVariantService.addVariant(request);
    }

    @GetMapping("/product/{id}")
    public List<ProductVariantResponse> getVariantsByProduct(@PathVariable Long id){
        return productVariantService.getVariantsByProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductVariantResponse updateVariant(@Valid @RequestBody ProductVariantRequest request,@PathVariable Long id){
        return productVariantService.updateVariant(request,id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteVariant(@PathVariable Long id){
        return productVariantService.deleteVariant(id);
    }

}
