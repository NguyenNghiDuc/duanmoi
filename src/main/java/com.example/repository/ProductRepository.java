package com.example.foodweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodweb.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByPriceBetween(double min, double max, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, double min, double max, Pageable pageable);

}