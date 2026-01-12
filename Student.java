package com.placement.sms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Student Entity - Represents a student in the placement system
 * 
 * This class uses:
 * - JPA annotations for database mapping
 * - Lombok annotations to reduce boilerplate code
 * - Validation annotations to ensure data integrity
 * 
 * @author Student Management System
 * @version 1.0
 */
@Entity
@Table(name = "students", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_department", columnList = "department")
})
@Data // Lombok: Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Generates no-args constructor
@AllArgsConstructor // Lombok: Generates all-args constructor
@Builder // Lombok: Implements Builder pattern
public class Student {
    
    /**
     * Primary Key - Auto-generated student ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    
    /**
     * Student Name - Required field with length constraints
     */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * Email - Must be unique and valid format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    /**
     * Department - Student's academic department
     */
    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
    @Column(name = "department", nullable = false, length = 50)
    private String department;
    
    /**
     * Year - Academic year (1-4 for undergraduate)
     */
    @NotNull(message = "Year is required")
    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 4, message = "Year must be at most 4")
    @Column(name = "year", nullable = false)
    private Integer year;
    
    /**
     * Phone Number - Contact number with validation
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;
    
    /**
     * Created At - Automatically set when record is created
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Updated At - Automatically updated when record is modified
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Custom constructor for creating student without ID
     * (ID is auto-generated)
     */
    public Student(String name, String email, String department, Integer year, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
        this.phoneNumber = phoneNumber;
    }
}
