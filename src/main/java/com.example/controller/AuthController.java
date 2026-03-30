package com.example.foodweb.controller;

import com.example.foodweb.model.User;
import com.example.foodweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

@Autowired
private UserRepository userRepository;

/* trang login */
@GetMapping("/login")
public String login(){
return "login";
}

/* trang register */
@GetMapping("/register")
public String register(){
return "register";
}

/* xử lý đăng ký */
@PostMapping("/register")
public String registerUser(User user){
userRepository.save(user);
return "redirect:/login";
}

}