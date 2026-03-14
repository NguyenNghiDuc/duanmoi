package com.example.foodweb.controller;

import com.example.foodweb.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orders")
    public String listOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderRepository.findAll().stream()
                .filter(o -> o.getUsername().equals(principal.getName())).toList());

        return "orders";
    }

    @GetMapping("/admin/orders")
    public String adminOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders-admin";
    }

    @PostMapping("/admin/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam String status) {
        orderRepository.findById(id).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
        return "redirect:/admin/orders";
    }
}