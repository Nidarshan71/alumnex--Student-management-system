package com.placement.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig - Web Configuration
 * 
 * Configures CORS (Cross-Origin Resource Sharing) to allow frontend
 * to access the backend API from different domains
 * 
 * CORS is needed because:
 * - Frontend runs on one port (e.g., 5500 with Live Server)
 * - Backend runs on another port (e.g., 8080)
 * - Browsers block cross-origin requests by default for security
 * 
 * @Configuration - Marks this as a Spring configuration class
 * 
 * @author Student Management System
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * Configure CORS mappings
     * 
     * Allows:
     * - All origins (in production, specify exact frontend URL)
     * - All HTTP methods (GET, POST, PUT, DELETE, etc.)
     * - All headers
     * - Credentials (cookies, authorization headers)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply to all /api/** endpoints
                .allowedOrigins("*") // Allow all origins (change in production)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false) // Must be false when allowedOrigins is "*"
                .maxAge(3600); // Cache preflight response for 1 hour
    }
}
