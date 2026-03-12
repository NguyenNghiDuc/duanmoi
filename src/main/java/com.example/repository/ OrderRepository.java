package com.example.foodweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodweb.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}