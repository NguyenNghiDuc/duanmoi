package com.example.foodweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class FoodWebApplication implements ApplicationListener<WebServerInitializedEvent> {

    public static void main(String[] args) {
        SpringApplication.run(FoodWebApplication.class, args);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int port = event.getWebServer().getPort();
        System.out.println("===============================================");
        System.out.println("FoodWeb is running at http://localhost:" + port + "/");
        System.out.println("H2 console available at http://localhost:" + port + "/h2");
        System.out.println("===============================================");
    }

}