package com.example.foodweb.controller;

import com.example.foodweb.model.Product;
import com.example.foodweb.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String listProducts(Model model) {

        model.addAttribute("products",
                productService.getAllProducts());

        return "index";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model) {

        model.addAttribute("product", new Product());

        return "product-form";
    }

    @PostMapping("/save-product")
    public String saveProduct(Product product) {

        productService.saveProduct(product);

        return "redirect:/products";
    }

}