package com.example.foodweb.controller;

import com.example.foodweb.repository.OrderRepository;
import com.example.foodweb.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // ================= USER ORDERS =================

    @GetMapping("/orders")
    public String listOrders(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        List<Order> orders = orderRepository.findByUsername(username);

        model.addAttribute("orders", orders);

        return "orders";
    }

    // ================= ADMIN ORDERS =================

    @GetMapping("/admin/orders")
    public String adminOrders(Model model) {

        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orders", orders);

        return "orders-admin";
    }

    // ================= UPDATE STATUS =================

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