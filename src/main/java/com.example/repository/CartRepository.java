package com.example.foodweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodweb.model.CartItem;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUsername(String username);

    void deleteByUsername(String username);

}