package com.placement.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * StudentManagementSystemApplication - Main Application Class
 * 
 * This is the entry point of the Spring Boot application
 * 
 * @SpringBootApplication is a convenience annotation that combines:
 * - @Configuration: Marks this class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to auto-configure based on dependencies
 * - @ComponentScan: Tells Spring to scan this package and sub-packages for components
 * 
 * What happens when you run this application:
 * 1. Spring Boot starts the embedded Tomcat server
 * 2. Scans all packages under com.placement.sms
 * 3. Creates beans for all @Component, @Service, @Repository, @Controller classes
 * 4. Configures database connection
 * 5. Sets up JPA/Hibernate
 * 6. Initializes Spring Security
 * 7. Starts listening on port 8080 (or configured port)
 * 
 * @author Student Management System
 * @version 1.0
 */
@SpringBootApplication
public class StudentManagementSystemApplication {
    
    /**
     * Main method - Application entry point
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Student Management System Started Successfully!");
        System.out.println("=".repeat(60));
        System.out.println("Server is running on: http://localhost:8080");
        System.out.println("API Base URL: http://localhost:8080/api/students");
        System.out.println("=".repeat(60) + "\n");
    }
}
