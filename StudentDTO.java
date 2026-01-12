package com.placement.studentms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) for Student
 * 
 * Why use DTOs?
 * 1. Separation of concerns - Entity represents database, DTO represents API contract
 * 2. Security - Don't expose internal entity structure directly
 * 3. Flexibility - Can transform data as needed for API
 * 4. Validation - Can have different validation rules than entity
 * 
 * Interview Tip: DTOs are best practice in enterprise applications for API layer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department name cannot exceed 50 characters")
    private String department;

    @NotNull(message = "Year is required")
    @Min(value = 1, message = "Year must be between 1 and 4")
    @Max(value = 4, message = "Year must be between 1 and 4")
    private Integer year;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
}
