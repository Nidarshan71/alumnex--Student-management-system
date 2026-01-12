package com.placement.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig - Security Configuration
 * 
 * Configures Spring Security for the application
 * For this project, we're using basic authentication with admin role
 * 
 * In a real application, you would:
 * - Implement JWT-based authentication
 * - Store users in database
 * - Have proper role-based access control
 * 
 * Current setup (simplified for learning):
 * - Basic HTTP authentication
 * - Single admin user (configured in application.properties)
 * - All API endpoints require authentication
 * 
 * @Configuration - Marks this as a Spring configuration class
 * @EnableWebSecurity - Enables Spring Security
 * 
 * @author Student Management System
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Configure HTTP security
     * 
     * This method defines:
     * - Which endpoints require authentication
     * - Which endpoints are public
     * - What type of authentication to use
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for REST API (stateless)
            .csrf(csrf -> csrf.disable())
            
            // Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (no authentication required)
                .requestMatchers("/api/students/**").permitAll() // Allow all for simplicity
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            
            // Enable HTTP Basic authentication
            .httpBasic(basic -> {});
        
        return http.build();
    }
    
    /**
     * Password encoder bean
     * 
     * BCryptPasswordEncoder is a strong password hashing algorithm
     * It automatically handles:
     * - Salting (random data added to password)
     * - Multiple rounds of hashing
     * - Secure password storage
     * 
     * Usage:
     * - When creating user: passwordEncoder.encode(plainPassword)
     * - When checking: passwordEncoder.matches(plainPassword, hashedPassword)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
