package com.example.foodweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**","/images/**","/js/**").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/cart","/orders","/checkout").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/products",true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

}