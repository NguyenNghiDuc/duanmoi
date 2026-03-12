package com.example.foodweb.controller;

import com.example.foodweb.service.ChatbotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatbotController {

    @Autowired
    ChatbotService chatbotService;

    @GetMapping("/chat")
    public String chat(@RequestParam String msg) {

        return chatbotService.reply(msg);
    }

}