package com.example.foodweb.controller;

import com.example.foodweb.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

@GetMapping("/")
public String home(Model model){

List<Product> products = new ArrayList<>();

products.add(new Product(1L,"Pizza Hải Sản",120000,"pizza.jpg"));
products.add(new Product(2L,"Pizza Phô Mai",110000,"pizza2.jpg"));
products.add(new Product(3L,"Pizza Pepperoni",115000,"pizza3.jpg"));
products.add(new Product(4L,"Pizza Gà BBQ",118000,"pizza4.jpg"));
products.add(new Product(5L,"Pizza Bò",125000,"pizza5.jpg"));

products.add(new Product(6L,"Burger Bò Phô Mai",85000,"burger.jpg"));
products.add(new Product(7L,"Burger Gà",80000,"burger2.jpg"));
products.add(new Product(8L,"Burger Cá",78000,"burger3.jpg"));
products.add(new Product(9L,"Burger Double",95000,"burger4.jpg"));
products.add(new Product(10L,"Burger BBQ",90000,"burger5.jpg"));

products.add(new Product(11L,"Trà Sữa Trân Châu",35000,"trasua.jpg"));
products.add(new Product(12L,"Trà Sữa Matcha",38000,"trasua2.jpg"));
products.add(new Product(13L,"Trà Đào",30000,"tradao.jpg"));
products.add(new Product(14L,"Coca Cola",20000,"coke.jpg"));
products.add(new Product(15L,"Pepsi",20000,"pepsi.jpg"));

model.addAttribute("products",products);
model.addAttribute("currentPage", 0);
model.addAttribute("totalPages", 1);
model.addAttribute("q", "");
model.addAttribute("minPrice", 0);
model.addAttribute("maxPrice", 0);

return "index";
}

}