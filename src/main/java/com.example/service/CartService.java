package com.example.foodweb.service;

import com.example.foodweb.model.CartItem;
import com.example.foodweb.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<CartItem> getCartItems(){
        return cartRepository.findAll();
    }

    public void addProductToCart(Long productId){

        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setQuantity(1);

        cartRepository.save(item);
    }

    public void removeCartItem(Long id){
        cartRepository.deleteById(id);
    }

    public void clearCart(){
        cartRepository.deleteAll();
    }
}