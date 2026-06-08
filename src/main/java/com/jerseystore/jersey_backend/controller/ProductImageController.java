package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.response.ProductImageResponse;
import com.jerseystore.jersey_backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/product/{productId}", consumes = "multipart/form-data")
    public ProductImageResponse addImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("isPrimary") Boolean isPrimary) {
        return productImageService.addImage(productId, file, isPrimary);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id){
        return productImageService.deleteImage(id);
    }

    @GetMapping("/product/{productId}")
    public List<ProductImageResponse> getImagesByProduct(@PathVariable Long productId){
        return productImageService.getImagesByProduct(productId);
    }
}
