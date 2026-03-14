package com.example.foodweb.controller;

import com.example.foodweb.model.Product;
import com.example.foodweb.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            Model model) {

        Page<Product> products = productService.searchProducts(q, minPrice, maxPrice, PageRequest.of(page, size));

        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("q", q);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "index";
    }

    @GetMapping({"/add-product", "/product/add"})
    public String addProductForm(Model model) {

        model.addAttribute("product", new Product());

        return "product-form";
    }

    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping({"/save-product", "/product/save"})
    public String saveProduct(Product product) {

        productService.saveProduct(product);

        return "redirect:/products";
    }

}