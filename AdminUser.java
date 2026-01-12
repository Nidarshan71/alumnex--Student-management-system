package com.placement.studentms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin User Entity - For authentication
 * 
 * Interview Tip: In production, you'd use Spring Security with BCrypt password encoding.
 * This is a simplified version for learning purposes.
 */
@Entity
@Table(name = "admin_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Password - Should be encrypted using BCrypt in production
     * Interview Tip: Never store plain text passwords! Always use encryption.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Role - For authorization (e.g., ADMIN, USER)
     * Interview Tip: In production, this would be a separate Role entity with Many-to-Many relationship
     */
    @Column(nullable = false)
    private String role = "ADMIN";
}
