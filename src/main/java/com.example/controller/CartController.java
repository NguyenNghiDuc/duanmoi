package com.example.foodweb.controller;

import com.example.foodweb.model.CartItem;
import com.example.foodweb.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model) {

        model.addAttribute("cartItems",
                cartService.getCartItems());

        return "cart";
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {

        cartService.addProductToCart(id);

        return "redirect:/cart";
    }

    @GetMapping("/remove-cart/{id}")
    public String removeCartItem(@PathVariable Long id) {

        cartService.removeCartItem(id);

        return "redirect:/cart";
    }

}