package com.placement.studentms.exception;

/**
 * Custom Exception for Resource Not Found scenarios
 * 
 * Why custom exceptions?
 * 1. More meaningful error messages
 * 2. Better exception handling
 * 3. Cleaner code
 * 4. Industry best practice
 * 
 * Interview Tip: Custom exceptions help in creating a robust error handling mechanism
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
