package com.placement.sms.service;

import com.placement.sms.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * StudentService Interface - Business Logic Layer
 * 
 * This interface defines all business operations for student management
 * Separating interface from implementation allows for:
 * - Easy testing (mocking)
 * - Multiple implementations
 * - Loose coupling
 * 
 * @author Student Management System
 * @version 1.0
 */
public interface StudentService {
    
    /**
     * Create a new student
     * 
     * @param student Student object to create
     * @return Created student with generated ID
     * @throws RuntimeException if email already exists
     */
    Student createStudent(Student student);
    
    /**
     * Get all students
     * 
     * @return List of all students
     */
    List<Student> getAllStudents();
    
    /**
     * Get student by ID
     * 
     * @param id Student ID
     * @return Student object if found
     * @throws RuntimeException if student not found
     */
    Student getStudentById(Long id);
    
    /**
     * Update existing student
     * 
     * @param id Student ID to update
     * @param studentDetails Updated student data
     * @return Updated student object
     * @throws RuntimeException if student not found or email already exists
     */
    Student updateStudent(Long id, Student studentDetails);
    
    /**
     * Delete student by ID
     * 
     * @param id Student ID to delete
     * @throws RuntimeException if student not found
     */
    void deleteStudent(Long id);
    
    /**
     * Search students by name or department
     * 
     * @param searchTerm Search keyword
     * @return List of matching students
     */
    List<Student> searchStudents(String searchTerm);
    
    /**
     * Get students by department
     * 
     * @param department Department name
     * @return List of students in the department
     */
    List<Student> getStudentsByDepartment(String department);
    
    /**
     * Get students by year
     * 
     * @param year Academic year
     * @return List of students in the year
     */
    List<Student> getStudentsByYear(Integer year);
    
    /**
     * Get students with pagination
     * 
     * @param pageable Pagination parameters
     * @return Page of students
     */
    Page<Student> getStudentsWithPagination(Pageable pageable);
    
    /**
     * Search students with pagination
     * 
     * @param searchTerm Search keyword
     * @param pageable Pagination parameters
     * @return Page of matching students
     */
    Page<Student> searchStudentsWithPagination(String searchTerm, Pageable pageable);
    
    /**
     * Get all departments
     * 
     * @return List of unique departments
     */
    List<String> getAllDepartments();
    
    /**
     * Get student count by department
     * 
     * @param department Department name
     * @return Number of students
     */
    long getStudentCountByDepartment(String department);
}
