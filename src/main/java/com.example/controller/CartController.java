package com.example.foodweb.controller;

import com.example.foodweb.model.CartItem;
import com.example.foodweb.model.Order;
import com.example.foodweb.model.Product;
import com.example.foodweb.repository.CartRepository;
import com.example.foodweb.repository.OrderRepository;
import com.example.foodweb.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    public static class CartEntry {
        private Product product;
        private int quantity;

        public CartEntry(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotal() {
            return product.getPrice() * quantity;
        }
    }

    // ================= ADD TO CART =================

    @PostMapping("/cart/add")
    @ResponseBody
    public String addCart(@RequestParam Long id,
                          @RequestParam(defaultValue = "1") int quantity,
                          Principal principal) {

        if (principal == null) {
            return "unauthorized";
        }

        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            return "product_not_found";
        }

        String username = principal.getName();

        List<CartItem> cart = cartRepository.findByUsername(username);

        CartItem existing = cart.stream()
                .filter(i -> i.getProductId().equals(id))
                .findFirst()
                .orElse(null);

        if (existing != null) {

            existing.setQuantity(existing.getQuantity() + quantity);
            cartRepository.save(existing);

        } else {

            CartItem item = new CartItem();
            item.setUsername(username);
            item.setProductId(id);
            item.setQuantity(quantity);

            cartRepository.save(item);
        }

        return "added";
    }

    // ================= VIEW CART =================

    @GetMapping("/cart")
    public String viewCart(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        List<CartItem> cart = cartRepository.findByUsername(username);

        List<CartEntry> entries = new ArrayList<>();

        double total = 0;

        for (CartItem item : cart) {

            Optional<Product> productOpt = productRepository.findById(item.getProductId());

            if (productOpt.isPresent()) {

                CartEntry entry = new CartEntry(productOpt.get(), item.getQuantity());

                entries.add(entry);

                total += entry.getTotal();
            }
        }

        model.addAttribute("cartEntries", entries);
        model.addAttribute("cartTotal", total);

        return "cart";
    }

    // ================= UPDATE CART =================

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long id,
                             @RequestParam int quantity,
                             Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        List<CartItem> cart = cartRepository.findByUsername(username);

        for (CartItem item : cart) {

            if (item.getProductId().equals(id)) {

                if (quantity <= 0) {

                    cartRepository.delete(item);

                } else {

                    item.setQuantity(quantity);
                    cartRepository.save(item);
                }

                break;
            }
        }

        return "redirect:/cart";
    }

    // ================= REMOVE =================

    @PostMapping("/cart/remove")
    public String remove(@RequestParam Long id, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        List<CartItem> cart = cartRepository.findByUsername(username);

        for (CartItem item : cart) {

            if (item.getProductId().equals(id)) {

                cartRepository.delete(item);

                break;
            }
        }

        return "redirect:/cart";
    }

    // ================= CHECKOUT PAGE =================

    @GetMapping("/checkout")
    public String checkoutPage(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        List<CartItem> cart = cartRepository.findByUsername(username);

        List<CartEntry> entries = new ArrayList<>();

        double total = 0;

        for (CartItem item : cart) {

            Optional<Product> productOpt = productRepository.findById(item.getProductId());

            if (productOpt.isPresent()) {

                CartEntry entry = new CartEntry(productOpt.get(), item.getQuantity());

                entries.add(entry);

                total += entry.getTotal();
            }
        }

        model.addAttribute("cartEntries", entries);
        model.addAttribute("cartTotal", total);

        return "checkout";
    }

    // ================= CHECKOUT SUBMIT =================

    @PostMapping("/checkout")
    public String checkoutSubmit(@RequestParam String paymentMethod,
                                 Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        List<CartItem> cart = cartRepository.findByUsername(username);

        if (cart.isEmpty()) {
            return "redirect:/cart";
        }

        double total = 0;

        for (CartItem item : cart) {

            Optional<Product> productOpt = productRepository.findById(item.getProductId());

            if (productOpt.isPresent()) {

                total += productOpt.get().getPrice() * item.getQuantity();
            }
        }

        Order order = new Order();

        order.setUsername(username);
        order.setTotal(total);
        order.setStatus("PAID");
        order.setPaymentMethod(paymentMethod);

        orderRepository.save(order);

        cartRepository.deleteByUsername(username);

        return "redirect:/orders";
    }
}