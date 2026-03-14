package com.example.foodweb.service;

import com.example.foodweb.model.Product;
import com.example.foodweb.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Page<Product> searchProducts(String keyword, Double minPrice, Double maxPrice, Pageable pageable) {
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null || maxPrice <= 0) maxPrice = Double.MAX_VALUE;
        if (keyword == null || keyword.isBlank()) {
            return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        }
        return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(keyword, minPrice, maxPrice, pageable);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}