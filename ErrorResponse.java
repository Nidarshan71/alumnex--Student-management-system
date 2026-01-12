package com.placement.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ErrorResponse - Data Transfer Object for Error Responses
 * 
 * Provides a standardized structure for all API error responses
 * Makes error handling consistent across the application
 * 
 * @author Student Management System
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Timestamp when error occurred
     */
    private LocalDateTime timestamp;
    
    /**
     * HTTP status code (404, 400, 500, etc.)
     */
    private int status;
    
    /**
     * Error type/name
     */
    private String error;
    
    /**
     * Detailed error message
     */
    private String message;
    
    /**
     * API path where error occurred
     */
    private String path;
    
    /**
     * Constructor without path
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
