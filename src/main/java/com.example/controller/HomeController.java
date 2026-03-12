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

        products.add(new Product(1L,"Pizza Hải Sản",120000,"/images/pizza.jpg"));
        products.add(new Product(2L,"Pizza Phô Mai",110000,"/images/pizza2.jpg"));
        products.add(new Product(3L,"Pizza Pepperoni",115000,"/images/pizza3.jpg"));
        products.add(new Product(4L,"Pizza Gà BBQ",118000,"/images/pizza4.jpg"));
        products.add(new Product(5L,"Pizza Bò",125000,"/images/pizza5.jpg"));

        products.add(new Product(6L,"Burger Bò Phô Mai",85000,"/images/burger.jpg"));
        products.add(new Product(7L,"Burger Gà",80000,"/images/burger2.jpg"));
        products.add(new Product(8L,"Burger Cá",78000,"/images/burger3.jpg"));
        products.add(new Product(9L,"Burger Double",95000,"/images/burger4.jpg"));
        products.add(new Product(10L,"Burger BBQ",90000,"/images/burger5.jpg"));

        products.add(new Product(11L,"Trà Sữa Trân Châu",35000,"/images/trasua.jpg"));
        products.add(new Product(12L,"Trà Sữa Matcha",38000,"/images/trasua2.jpg"));
        products.add(new Product(13L,"Trà Đào",30000,"/images/tradao.jpg"));
        products.add(new Product(14L,"Coca Cola",20000,"/images/coke.jpg"));
        products.add(new Product(15L,"Pepsi",20000,"/images/pepsi.jpg"));

        products.add(new Product(16L,"Gà Rán Giòn",90000,"/images/garan.jpg"));
        products.add(new Product(17L,"Gà Rán Cay",92000,"/images/garan2.jpg"));
        products.add(new Product(18L,"Khoai Tây Chiên",45000,"/images/fries.jpg"));
        products.add(new Product(19L,"Khoai Tây Lắc Phô Mai",48000,"/images/fries2.jpg"));
        products.add(new Product(20L,"Hotdog",40000,"/images/hotdog.jpg"));

        products.add(new Product(21L,"Kem Vanilla",25000,"/images/kem.jpg"));
        products.add(new Product(22L,"Kem Chocolate",25000,"/images/kem2.jpg"));
        products.add(new Product(23L,"Kem Dâu",25000,"/images/kem3.jpg"));
        products.add(new Product(24L,"Bánh Flan",30000,"/images/flan.jpg"));
        products.add(new Product(25L,"Bánh Waffle",35000,"/images/waffle.jpg"));

        model.addAttribute("products",products);

        return "index";
    }
}