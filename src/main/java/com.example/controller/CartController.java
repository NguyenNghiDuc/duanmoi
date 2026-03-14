package com.example.foodweb.controller;

import com.example.foodweb.model.CartItem;
import com.example.foodweb.model.Order;
import com.example.foodweb.model.Product;
import com.example.foodweb.repository.CartRepository;
import com.example.foodweb.repository.OrderRepository;
import com.example.foodweb.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
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

    private static class CartEntry {
        private final Product product;
        private int quantity;

        public CartEntry(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getTotal() { return product.getPrice() * quantity; }
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public String addCart(@RequestParam Long id,
                          @RequestParam(required = false, defaultValue = "1") int quantity,
                          Principal principal){

        if (principal == null) {
            return "unauthorized";
        }

        if (quantity < 1) {
            quantity = 1;
        }

        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return "product_not_found";
        }

        String username = principal.getName();
        List<CartItem> cart = cartRepository.findByUsername(username);

        CartItem existing = cart.stream().filter(it -> it.getProductId().equals(id)).findFirst().orElse(null);
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

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long id,
                             @RequestParam int quantity,
                             Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<CartItem> cart = cartRepository.findByUsername(username);

        if (quantity < 1) {
            cart.removeIf(item -> item.getProductId().equals(id));
        } else {
            for (CartItem item : cart) {
                if (item.getProductId().equals(id)) {
                    item.setQuantity(quantity);
                    cartRepository.save(item);
                    break;
                }
            }
        }
        cartRepository.deleteByUsername(username);
        cartRepository.saveAll(cart);

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<CartItem> cart = cartRepository.findByUsername(username);
        cart.removeIf(item -> item.getProductId().equals(id));
        cartRepository.deleteByUsername(username);
        cartRepository.saveAll(cart);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Principal principal, Model model){
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

    @PostMapping("/checkout")
    public String checkoutSubmit(Principal principal,
                                 @RequestParam String paymentMethod) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<CartItem> cart = cartRepository.findByUsername(username);
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        double total = 0;
        for (CartItem item : cart) {
            Optional<Product> productOpt = productRepository.findById(item.getProductId());
            if (productOpt.isPresent()) {
                total += productOpt.get().getPrice() * item.getQuantity();
            }
        }

        // Giả lập xử lý thanh toán
        if (!"cash".equals(paymentMethod) && !"card".equals(paymentMethod) && !"momo".equals(paymentMethod)) {
            return "redirect:/checkout";
        }

        Order order = new Order();
        order.setUsername(username);
        order.setTotal(total);
        order.setStatus("PAID");
        order.setPaymentMethod(paymentMethod);

        List<CartItem> orderItems = new ArrayList<>();
        for (CartItem item : cart) {
            CartItem ci = new CartItem();
            ci.setProductId(item.getProductId());
            ci.setQuantity(item.getQuantity());
            orderItems.add(ci);
        }
        order.setItems(orderItems);

        orderRepository.save(order);
        cartRepository.deleteByUsername(username);

        return "redirect:/orders";
    }
}