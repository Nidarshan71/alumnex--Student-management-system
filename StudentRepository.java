package com.placement.sms.repository;

import com.placement.sms.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentRepository - Data Access Layer
 * 
 * This interface extends JpaRepository which provides:
 * - Basic CRUD operations (save, findById, findAll, delete, etc.)
 * - Pagination and sorting
 * - Custom query methods
 * 
 * Spring Data JPA automatically implements this interface at runtime
 * 
 * @author Student Management System
 * @version 1.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Find student by email
     * Useful for checking duplicate emails
     * 
     * @param email Student's email
     * @return Optional containing student if found
     */
    Optional<Student> findByEmail(String email);
    
    /**
     * Find students by department
     * Returns all students in a specific department
     * 
     * @param department Department name
     * @return List of students
     */
    List<Student> findByDepartment(String department);
    
    /**
     * Find students by year
     * Returns all students in a specific academic year
     * 
     * @param year Academic year (1-4)
     * @return List of students
     */
    List<Student> findByYear(Integer year);
    
    /**
     * Find students by department and year
     * Useful for filtering students
     * 
     * @param department Department name
     * @param year Academic year
     * @return List of students
     */
    List<Student> findByDepartmentAndYear(String department, Integer year);
    
    /**
     * Search students by name (case-insensitive partial match)
     * Uses LIKE query to find students whose name contains the search term
     * 
     * @param name Name to search for
     * @return List of matching students
     */
    List<Student> findByNameContainingIgnoreCase(String name);
    
    /**
     * Search students by department (case-insensitive partial match)
     * 
     * @param department Department to search for
     * @return List of matching students
     */
    List<Student> findByDepartmentContainingIgnoreCase(String department);
    
    /**
     * Custom query to search students by name OR department
     * Uses @Query annotation with JPQL (Java Persistence Query Language)
     * 
     * @param searchTerm Term to search in name or department
     * @return List of matching students
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Student> searchStudents(@Param("searchTerm") String searchTerm);
    
    /**
     * Get students with pagination and search
     * Supports pagination for better performance with large datasets
     * 
     * @param searchTerm Term to search
     * @param pageable Pagination parameters (page number, size, sort)
     * @return Page of students
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Student> searchStudentsWithPagination(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    /**
     * Check if email already exists
     * Useful for validation before creating/updating student
     * 
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Count students by department
     * Useful for analytics and reporting
     * 
     * @param department Department name
     * @return Number of students in the department
     */
    long countByDepartment(String department);
    
    /**
     * Get all distinct departments
     * Useful for dropdowns and filters
     * 
     * @return List of unique department names
     */
    @Query("SELECT DISTINCT s.department FROM Student s ORDER BY s.department")
    List<String> findAllDepartments();
}
