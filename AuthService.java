package com.placement.studentms.service;

import com.placement.studentms.exception.DuplicateResourceException;
import com.placement.studentms.model.AdminUser;
import com.placement.studentms.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Authentication Service
 * 
 * Handles user registration and login
 * 
 * Note: This is a simplified version for learning.
 * In production, you should use:
 * 1. Spring Security with BCryptPasswordEncoder
 * 2. JWT tokens for session management
 * 3. More robust authentication mechanisms
 * 
 * Interview Tip: Always mention security best practices in interviews
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AdminUserRepository adminUserRepository;

    /**
     * Register a new admin user
     * 
     * In production, password should be encrypted using BCrypt:
     * String encodedPassword = passwordEncoder.encode(password);
     */
    public AdminUser register(String username, String password, String email) {
        // Check if username exists
        if (adminUserRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already exists");
        }

        // Check if email exists
        if (adminUserRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Create new admin user
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(username);
        adminUser.setPassword(password); // In production: encrypt this!
        adminUser.setEmail(email);
        adminUser.setRole("ADMIN");

        return adminUserRepository.save(adminUser);
    }

    /**
     * Login - Validate credentials
     * 
     * In production, use:
     * passwordEncoder.matches(rawPassword, encodedPassword)
     */
    public Optional<AdminUser> login(String username, String password) {
        Optional<AdminUser> userOpt = adminUserRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            AdminUser user = userOpt.get();
            // In production, use passwordEncoder.matches()
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }

    /**
     * Check if user exists
     */
    public boolean userExists(String username) {
        return adminUserRepository.existsByUsername(username);
    }
}
