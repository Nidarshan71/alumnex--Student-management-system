package com.placement.studentms.repository;

import com.placement.studentms.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Student Repository Interface
 * 
 * JpaRepository provides CRUD operations out of the box:
 * - save() - Create or Update
 * - findById() - Read by ID
 * - findAll() - Read all
 * - deleteById() - Delete by ID
 * - count() - Count records
 * - existsById() - Check if exists
 * 
 * Interview Tip: Spring Data JPA automatically implements this interface at runtime!
 * You just need to declare method signatures following naming conventions.
 * 
 * Method Naming Conventions:
 * - findBy[FieldName] - finds by a field
 * - findBy[Field1]And[Field2] - finds by multiple fields
 * - findBy[Field]OrderBy[AnotherField] - finds and sorts
 * - findBy[Field]Containing - for partial matches (LIKE query)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by email
     * Spring Data JPA auto-generates: SELECT * FROM students WHERE email = ?
     */
    Optional<Student> findByEmail(String email);

    /**
     * Check if student exists by email
     * Spring Data JPA auto-generates: SELECT COUNT(*) FROM students WHERE email = ?
     */
    boolean existsByEmail(String email);

    /**
     * Find students by department
     * Returns: List of students in that department
     */
    List<Student> findByDepartment(String department);

    /**
     * Find students by year
     */
    List<Student> findByYear(Integer year);

    /**
     * Find students by name containing (case-insensitive search)
     * Example: "john" will match "John", "Johnny", "John Doe"
     * SQL: SELECT * FROM students WHERE LOWER(name) LIKE LOWER('%keyword%')
     */
    List<Student> findByNameContainingIgnoreCase(String keyword);

    /**
     * Find students by department containing (case-insensitive search)
     */
    List<Student> findByDepartmentContainingIgnoreCase(String keyword);

    /**
     * Custom JPQL Query - Search by name OR department
     * @Query annotation allows custom queries
     * 
     * Interview Tip: Use @Query when method naming becomes too complex
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByNameOrDepartment(@Param("keyword") String keyword);

    /**
     * Pagination Support
     * Find all students with pagination and sorting
     * Pageable contains page number, size, and sort information
     */
    Page<Student> findAll(Pageable pageable);

    /**
     * Find by department with pagination
     */
    Page<Student> findByDepartment(String department, Pageable pageable);

    /**
     * Search with pagination
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Student> searchByNameOrDepartment(@Param("keyword") String keyword, Pageable pageable);
}
