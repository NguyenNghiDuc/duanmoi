package com.example.foodweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodweb.model.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long> {

}