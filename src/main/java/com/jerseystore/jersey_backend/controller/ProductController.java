package com.jerseystore.jersey_backend.controller;

import com.jerseystore.jersey_backend.dto.request.ProductRequest;
import com.jerseystore.jersey_backend.dto.response.ProductResponse;
import com.jerseystore.jersey_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse addProduct(@Valid @RequestBody ProductRequest request){
        return productService.addProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/team/{team}")
    public List<ProductResponse> searchByTeam(@PathVariable String team){
        return productService.searchByTeam(team);
    }

    @GetMapping("/league/{league}")
    public List<ProductResponse> searchByLeague(@PathVariable String league){
        return productService.searchByLeague(league);
    }

    @GetMapping("/brand/{brand}")
    public List<ProductResponse> searchByBrand(@PathVariable String brand){
        return productService.searchByBrand(brand);
    }

@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@Valid @RequestBody ProductRequest request,@PathVariable Long id){
        return productService.updateProduct(request,id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){

        return productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchByKeyword(@RequestParam String keyword) {
        return productService.searchByKeyword(keyword);
    }

    @GetMapping("/search/team")
    public List<ProductResponse> searchByTeamName(@RequestParam String team) {
        return productService.searchByTeamName(team);
    }

    @GetMapping("/search/league")
    public List<ProductResponse> searchByLeagueName(@RequestParam String league) {
        return productService.searchByLeagueName(league);
    }

    @GetMapping("/search/brand")
    public List<ProductResponse> searchByBrandName(@RequestParam String brand) {
        return productService.searchByBrandName(brand);
    }

}
