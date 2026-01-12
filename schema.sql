-- =====================================================
-- STUDENT MANAGEMENT SYSTEM - DATABASE SCHEMA
-- =====================================================
-- Database: MySQL 8.0+
-- Author: Student Management System
-- Version: 1.0
-- =====================================================

-- Create database
CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- =====================================================
-- STUDENTS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    year INT NOT NULL CHECK (year BETWEEN 1 AND 4),
    phone_number VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Indexes for better query performance
    INDEX idx_email (email),
    INDEX idx_department (department),
    INDEX idx_year (year),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- SAMPLE DATA (Optional - for testing)
-- =====================================================
INSERT INTO students (name, email, department, year, phone_number) VALUES
('Rajesh Kumar', 'rajesh.kumar@example.com', 'Computer Science', 3, '9876543210'),
('Priya Sharma', 'priya.sharma@example.com', 'Electronics', 2, '9876543211'),
('Amit Patel', 'amit.patel@example.com', 'Mechanical', 4, '9876543212'),
('Sneha Gupta', 'sneha.gupta@example.com', 'Computer Science', 3, '9876543213'),
('Vikram Singh', 'vikram.singh@example.com', 'Civil', 1, '9876543214'),
('Anita Desai', 'anita.desai@example.com', 'Electronics', 2, '9876543215'),
('Rahul Verma', 'rahul.verma@example.com', 'Computer Science', 4, '9876543216'),
('Kavita Reddy', 'kavita.reddy@example.com', 'Mechanical', 1, '9876543217'),
('Suresh Rao', 'suresh.rao@example.com', 'Civil', 3, '9876543218'),
('Deepa Nair', 'deepa.nair@example.com', 'Electronics', 4, '9876543219');

-- =====================================================
-- VERIFY DATA
-- =====================================================
SELECT 
    COUNT(*) as total_students,
    COUNT(DISTINCT department) as total_departments
FROM students;

SELECT department, COUNT(*) as student_count
FROM students
GROUP BY department
ORDER BY student_count DESC;

-- =====================================================
-- USEFUL QUERIES FOR TESTING
-- =====================================================

-- Get all students
-- SELECT * FROM students ORDER BY created_at DESC;

-- Search students by name or department
-- SELECT * FROM students 
-- WHERE name LIKE '%kumar%' OR department LIKE '%computer%';

-- Get students by department
-- SELECT * FROM students WHERE department = 'Computer Science';

-- Get students by year
-- SELECT * FROM students WHERE year = 3;

-- Count students by department
-- SELECT department, COUNT(*) as count 
-- FROM students 
-- GROUP BY department;

-- Get all unique departments
-- SELECT DISTINCT department FROM students ORDER BY department;

-- =====================================================
-- INDEXES EXPLANATION
-- =====================================================
-- idx_email: For quick email lookups (login, uniqueness check)
-- idx_department: For filtering by department
-- idx_year: For filtering by year
-- idx_created_at: For sorting by creation date

-- =====================================================
-- PERFORMANCE TIPS
-- =====================================================
-- 1. Always use indexed columns in WHERE clauses
-- 2. Use LIMIT for pagination
-- 3. Use COUNT(*) instead of COUNT(column)
-- 4. Avoid SELECT * in production (specify columns)
-- 5. Use prepared statements to prevent SQL injection

-- =====================================================
-- BACKUP COMMANDS
-- =====================================================
-- Backup database:
-- mysqldump -u root -p student_management_db > backup.sql

-- Restore database:
-- mysql -u root -p student_management_db < backup.sql
