package com.example.foodweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodweb.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}