package com.example.foodweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.foodweb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}