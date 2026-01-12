package com.placement.sms.exception;

/**
 * DuplicateResourceException - Custom Exception
 * 
 * Thrown when trying to create a resource that already exists
 * For example: Student with duplicate email
 * 
 * @author Student Management System
 * @version 1.0
 */
public class DuplicateResourceException extends RuntimeException {
    
    /**
     * Constructor with message
     * 
     * @param message Error message describing the duplicate
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * 
     * @param message Error message
     * @param cause Original exception that caused this
     */
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
