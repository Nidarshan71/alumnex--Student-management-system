package com.placement.studentms.exception;

/**
 * Custom Exception for Duplicate Resource scenarios
 */
public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
}

/**
 * Custom Exception for Invalid Credentials
 */
class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

/**
 * Custom Exception for Bad Request scenarios
 */
class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
