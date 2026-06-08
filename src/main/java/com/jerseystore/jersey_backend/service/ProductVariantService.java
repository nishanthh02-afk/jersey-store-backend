package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.ProductVariantRequest;
import com.jerseystore.jersey_backend.dto.response.ProductVariantResponse;
import com.jerseystore.jersey_backend.entity.Product;
import com.jerseystore.jersey_backend.entity.ProductVariant;
import com.jerseystore.jersey_backend.repository.ProductRepository;
import com.jerseystore.jersey_backend.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    public ProductVariantResponse addVariant(ProductVariantRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .kitType(request.getKitType())
                .size(request.getSize())
                .stock(request.getStock())
                .price(request.getPrice())
                .build();
        productVariantRepository.save(variant);
        return convertToResponse(variant);
    }

    public List<ProductVariantResponse> getVariantsByProduct(Long id) {

        Product product=productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        return productVariantRepository.findByProduct(product)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProductVariantResponse updateVariant(ProductVariantRequest request,Long id) {

        ProductVariant productVariant=productVariantRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product variant not found"));

        productVariant.setKitType(request.getKitType());
        productVariant.setSize(request.getSize());
        productVariant.setStock(request.getStock());
        productVariant.setPrice(request.getPrice());
        productVariantRepository.save(productVariant);
        return convertToResponse(productVariant);
    }

    public String deleteVariant(Long id) {
        ProductVariant productVariant=productVariantRepository.findById(id)
                .orElseThrow(()->new RuntimeException("ProductVariant not found"));
        productVariantRepository.deleteById(id);
        return "Product deleted successfully";
    }

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    public ProductVariantResponse convertToResponse(ProductVariant variant){
        return new ProductVariantResponse(
                variant.getId(),
                variant.getProduct().getId(),
                variant.getKitType(),
                variant.getSize(),
                variant.getStock(),
                variant.getPrice()
        );
    }

}
