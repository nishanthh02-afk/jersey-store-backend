package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.response.ProductImageResponse;
import com.jerseystore.jersey_backend.entity.Product;
import com.jerseystore.jersey_backend.entity.ProductImage;
import com.jerseystore.jersey_backend.repository.ProductImageRepository;
import com.jerseystore.jersey_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductImageService {

    public ProductImageResponse addImage(Long productId, MultipartFile file, Boolean isPrimary) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String imageUrl = cloudinaryService.uploadImage(file);

        ProductImage image = new ProductImage();
        image.setProduct(product);
        image.setImageUrl(imageUrl);
        image.setIsPrimary(isPrimary != null ? isPrimary : false);

        productImageRepository.save(image);
        return convertToResponse(image);
    }

    public String deleteImage(Long id) {
        ProductImage productImage=productImageRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Image not available"));
        cloudinaryService.deleteImage(productImage.getImageUrl());
        productImageRepository.deleteById(id);
        return "Image deleted successfully";
    }

    public List<ProductImageResponse> getImagesByProduct(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Image not available"));
        return productImageRepository.findByProduct(product)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private final CloudinaryService cloudinaryService;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    public ProductImageResponse convertToResponse(ProductImage productImage){
        return new ProductImageResponse(
                productImage.getId(),
                productImage.getProduct().getId(),
                productImage.getImageUrl(),
                productImage.getIsPrimary()
        );
    }
}
