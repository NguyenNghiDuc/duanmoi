package com.example.foodweb.controller;

import com.example.foodweb.service.ChatbotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatbotController {

    @Autowired
    ChatbotService chatbotService;

    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }

    @GetMapping("/chat/api")
    @ResponseBody
    public String chatApi(@RequestParam String msg) {
        return chatbotService.reply(msg);
    }

}