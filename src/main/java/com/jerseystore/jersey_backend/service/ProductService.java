package com.jerseystore.jersey_backend.service;

import com.jerseystore.jersey_backend.dto.request.ProductRequest;
import com.jerseystore.jersey_backend.dto.response.ProductResponse;
import com.jerseystore.jersey_backend.entity.Product;
import com.jerseystore.jersey_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public ProductResponse addProduct(ProductRequest request) {

        Product product=Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .team(request.getTeam())
                .league(request.getLeague())
                .brand(request.getBrand())
                .build();

        productRepository.save(product);

        return convertToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
       Product product=productRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("product not found"));
       return convertToResponse(product);
    }

    public List<ProductResponse> searchByTeam(String team) {
        return productRepository.findByTeam(team)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByLeague(String league) {
        return productRepository.findByLeague(league)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(ProductRequest request,Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setTeam(request.getTeam());
        product.setLeague(request.getLeague());
        product.setBrand(request.getBrand());
        productRepository.save(product);
        return convertToResponse(product);
    }

    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }

    public List<ProductResponse> searchByKeyword(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByTeamName(String team) {
        return productRepository.findByTeamContainingIgnoreCase(team)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByLeagueName(String league) {
        return productRepository.findByLeagueContainingIgnoreCase(league)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByBrandName(String brand) {
        return productRepository.findByBrandContainingIgnoreCase(brand)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    private final ProductRepository productRepository;
    private ProductResponse convertToResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getTeam(),
                product.getLeague(),
                product.getBrand()
        );
    }



}
