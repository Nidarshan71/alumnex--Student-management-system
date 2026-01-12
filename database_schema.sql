-- ========================================
-- STUDENT MANAGEMENT SYSTEM - DATABASE SCHEMA
-- ========================================
-- Database: MySQL
-- Author: Interview-Ready Project
-- Date: 2024
-- ========================================

-- Create Database
CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- ========================================
-- TABLE: admin_users
-- Purpose: Store admin user credentials for authentication
-- ========================================

CREATE TABLE IF NOT EXISTS admin_users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',
    
    -- Indexes for faster lookups
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default admin user (Password: admin123)
-- Note: In production, this password should be encrypted using BCrypt
INSERT INTO admin_users (username, password, email, role) 
VALUES ('admin', 'admin123', 'admin@example.com', 'ADMIN');

-- ========================================
-- TABLE: students
-- Purpose: Store student information
-- ========================================

CREATE TABLE IF NOT EXISTS students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    year INT NOT NULL CHECK (year >= 1 AND year <= 4),
    phone_number VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Indexes for faster searches and filtering
    INDEX idx_email (email),
    INDEX idx_department (department),
    INDEX idx_year (year),
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- SAMPLE DATA - For Testing
-- ========================================

INSERT INTO students (name, email, department, year, phone_number) VALUES
('Rajesh Kumar', 'rajesh.kumar@example.com', 'Computer Science', 3, '9876543210'),
('Priya Sharma', 'priya.sharma@example.com', 'Electronics', 2, '9876543211'),
('Amit Patel', 'amit.patel@example.com', 'Mechanical', 4, '9876543212'),
('Sneha Reddy', 'sneha.reddy@example.com', 'Computer Science', 1, '9876543213'),
('Vikram Singh', 'vikram.singh@example.com', 'Civil', 3, '9876543214'),
('Anjali Verma', 'anjali.verma@example.com', 'Electrical', 2, '9876543215'),
('Rahul Gupta', 'rahul.gupta@example.com', 'Computer Science', 4, '9876543216'),
('Pooja Desai', 'pooja.desai@example.com', 'Electronics', 1, '9876543217'),
('Kiran Joshi', 'kiran.joshi@example.com', 'Mechanical', 2, '9876543218'),
('Neha Kapoor', 'neha.kapoor@example.com', 'Computer Science', 3, '9876543219');

-- ========================================
-- USEFUL QUERIES FOR INTERVIEWS
-- ========================================

-- Q1: Get all students
-- SELECT * FROM students;

-- Q2: Get students by department
-- SELECT * FROM students WHERE department = 'Computer Science';

-- Q3: Get students by year
-- SELECT * FROM students WHERE year = 3;

-- Q4: Search students by name (partial match)
-- SELECT * FROM students WHERE name LIKE '%kumar%';

-- Q5: Count students by department
-- SELECT department, COUNT(*) as student_count 
-- FROM students 
-- GROUP BY department 
-- ORDER BY student_count DESC;

-- Q6: Get students sorted by name
-- SELECT * FROM students ORDER BY name ASC;

-- Q7: Get recently added students
-- SELECT * FROM students ORDER BY created_at DESC LIMIT 10;

-- Q8: Find students with duplicate emails (data validation)
-- SELECT email, COUNT(*) as count 
-- FROM students 
-- GROUP BY email 
-- HAVING count > 1;

-- Q9: Get students with pagination (page 1, 10 records per page)
-- SELECT * FROM students LIMIT 10 OFFSET 0;

-- Q10: Search in multiple fields
-- SELECT * FROM students 
-- WHERE name LIKE '%kumar%' OR department LIKE '%computer%';

-- ========================================
-- DATABASE INTERVIEW QUESTIONS
-- ========================================

/*
Q1: What is the difference between VARCHAR and CHAR?
A: VARCHAR is variable length (uses only space needed + 1-2 bytes for length)
   CHAR is fixed length (always uses specified length, padded with spaces)
   VARCHAR is more space-efficient for varying data like names, emails

Q2: Why use BIGINT for ID instead of INT?
A: BIGINT supports much larger numbers (up to 2^63-1)
   For scalable applications, BIGINT prevents running out of IDs
   INT max is ~2 billion, BIGINT max is ~9 quintillion

Q3: What is AUTO_INCREMENT?
A: Automatically generates a unique number for new records
   Commonly used for primary keys
   Increments by 1 for each new record

Q4: Why use UNIQUE constraint on email?
A: Ensures no duplicate emails in database
   Prevents data integrity issues
   Enforces business rule: one email per student

Q5: What is the purpose of INDEX?
A: Speeds up data retrieval operations
   Similar to book index - helps find data quickly
   Trade-off: Slightly slower writes, much faster reads

Q6: What is ENGINE=InnoDB?
A: MySQL storage engine that supports:
   - ACID transactions
   - Foreign keys
   - Row-level locking
   - Crash recovery
   Best choice for most applications

Q7: What is DEFAULT CHARSET=utf8mb4?
A: Character encoding that supports:
   - All Unicode characters
   - Emojis
   - International languages
   utf8mb4 is the "true" UTF-8 in MySQL

Q8: What is CHECK constraint?
A: Validates data before insertion
   Example: CHECK (year >= 1 AND year <= 4)
   Ensures only valid years (1-4) are stored

Q9: What is TIMESTAMP vs DATETIME?
A: TIMESTAMP: Stores Unix timestamp, auto-converts timezones, range 1970-2038
   DATETIME: Stores as string, no timezone conversion, range 1000-9999
   Use TIMESTAMP for automatic tracking

Q10: What is ON UPDATE CURRENT_TIMESTAMP?
A: Automatically updates the field to current time when record is modified
   Perfect for "updated_at" audit fields
   Helps track when data was last changed
*/

-- ========================================
-- PERFORMANCE OPTIMIZATION
-- ========================================

-- Analyze table to update statistics
ANALYZE TABLE students;

-- Check table status
SHOW TABLE STATUS LIKE 'students';

-- Explain query execution plan (useful for optimization)
-- EXPLAIN SELECT * FROM students WHERE department = 'Computer Science';
