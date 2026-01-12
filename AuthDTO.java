package com.placement.studentms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication Request DTOs
 */

// Login Request DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
class LoginRequest {
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}

// Register Request DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
class RegisterRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}

// Authentication Response DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
class AuthResponse {
    private String message;
    private String username;
    private String role;
}
