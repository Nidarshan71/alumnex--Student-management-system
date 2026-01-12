package com.placement.studentms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Student Entity - Represents a student in the database
 * 
 * Annotations Explained:
 * @Entity - Marks this class as a JPA entity (maps to database table)
 * @Table - Specifies the table name in database
 * @Data - Lombok annotation that generates getters, setters, toString, equals, hashCode
 * @NoArgsConstructor - Generates no-argument constructor (required by JPA)
 * @AllArgsConstructor - Generates constructor with all fields
 * 
 * Interview Tip: This follows the "Entity" pattern in Spring Boot's layered architecture
 */
@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * Primary Key
     * @Id - Marks this field as the primary key
     * @GeneratedValue - Specifies how the primary key should be generated
     * IDENTITY strategy means the database will auto-increment this value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Student Name
     * @NotBlank - Validation: ensures field is not null and not empty
     * @Size - Validation: sets min and max length constraints
     * @Column - Specifies column properties (nullable = false means NOT NULL in DB)
     */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Student Email
     * @Email - Validation: ensures valid email format
     * unique = true ensures no duplicate emails in database
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Department
     */
    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department name cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String department;

    /**
     * Year of Study
     * @Min and @Max - Validation: ensures year is between 1 and 4
     */
    @NotNull(message = "Year is required")
    @Min(value = 1, message = "Year must be between 1 and 4")
    @Max(value = 4, message = "Year must be between 1 and 4")
    @Column(nullable = false)
    private Integer year;

    /**
     * Phone Number
     * @Pattern - Validation: uses regex to ensure valid 10-digit phone number
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, length = 10)
    private String phoneNumber;

    /**
     * Audit Fields
     * @CreationTimestamp - Automatically sets timestamp when record is created
     * @UpdateTimestamp - Automatically updates timestamp when record is modified
     * 
     * Interview Tip: These are called "Audit Fields" and are best practice for tracking data changes
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
