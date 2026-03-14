package com.example.foodweb.config;

import com.example.foodweb.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/",
                            "/products",
                            "/register",
                            "/chat",
                            "/css/**",
                            "/js/**",
                            "/images/**"
                    ).permitAll()
                    .requestMatchers("/product/**", "/add-product", "/save-product").hasRole("ADMIN")
                    .requestMatchers("/cart/**", "/orders").authenticated()
                    .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/products", true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/")
                    .permitAll()
            );

        return http.build();
    }

}