package com.example.foodweb.service;

import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    public String reply(String msg){

        msg = msg.toLowerCase();

        if(msg.contains("menu")){
            return "Menu gồm: Pizza, Burger, Trà sữa";
        }

        if(msg.contains("giá")){
            return "Giá món ăn từ 30k đến 120k";
        }

        if(msg.contains("ship")){
            return "Chúng tôi giao hàng toàn quốc";
        }

        if(msg.contains("xin chào")){
            return "Xin chào! Tôi là chatbot của FoodWeb";
        }

        return "Xin lỗi, tôi chưa hiểu câu hỏi.";
    }
}