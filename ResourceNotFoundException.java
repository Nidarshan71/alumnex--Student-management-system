package com.placement.sms.exception;

/**
 * ResourceNotFoundException - Custom Exception
 * 
 * Thrown when a requested resource (like Student) is not found
 * Extends RuntimeException so it's an unchecked exception
 * 
 * @author Student Management System
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructor with message
     * 
     * @param message Error message describing what was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * 
     * @param message Error message
     * @param cause Original exception that caused this
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
