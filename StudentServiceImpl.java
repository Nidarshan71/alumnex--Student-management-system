package com.placement.sms.service;

import com.placement.sms.exception.ResourceNotFoundException;
import com.placement.sms.exception.DuplicateResourceException;
import com.placement.sms.model.Student;
import com.placement.sms.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * StudentServiceImpl - Business Logic Implementation
 * 
 * This class implements all business logic for student management
 * - Validates data before database operations
 * - Handles exceptions
 * - Implements transaction management
 * - Logs operations for debugging
 * 
 * @Slf4j - Lombok annotation for logging
 * @Service - Marks this as a Spring service component
 * @Transactional - Ensures database consistency
 * 
 * @author Student Management System
 * @version 1.0
 */
@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    
    /**
     * Constructor injection (recommended over field injection)
     * Spring automatically injects StudentRepository
     */
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    /**
     * Create a new student
     * Validates that email doesn't already exist
     */
    @Override
    public Student createStudent(Student student) {
        log.info("Creating new student with email: {}", student.getEmail());
        
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            log.error("Email already exists: {}", student.getEmail());
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists");
        }
        
        // Save student to database
        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getStudentId());
        
        return savedStudent;
    }
    
    /**
     * Get all students from database
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        log.info("Found {} students", students.size());
        return students;
    }
    
    /**
     * Get student by ID
     * Throws exception if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        
        return studentRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Student not found with ID: {}", id);
                return new ResourceNotFoundException("Student not found with ID: " + id);
            });
    }
    
    /**
     * Update existing student
     * Validates email uniqueness (if changed)
     */
    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        log.info("Updating student with ID: {}", id);
        
        // Find existing student
        Student existingStudent = getStudentById(id);
        
        // Check if email is being changed and if new email already exists
        if (!existingStudent.getEmail().equals(studentDetails.getEmail())) {
            if (studentRepository.existsByEmail(studentDetails.getEmail())) {
                log.error("Email already exists: {}", studentDetails.getEmail());
                throw new DuplicateResourceException("Student with email " + studentDetails.getEmail() + " already exists");
            }
        }
        
        // Update fields
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setDepartment(studentDetails.getDepartment());
        existingStudent.setYear(studentDetails.getYear());
        existingStudent.setPhoneNumber(studentDetails.getPhoneNumber());
        
        // Save updated student
        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Student updated successfully with ID: {}", updatedStudent.getStudentId());
        
        return updatedStudent;
    }
    
    /**
     * Delete student by ID
     */
    @Override
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        
        // Verify student exists
        Student student = getStudentById(id);
        
        // Delete student
        studentRepository.delete(student);
        log.info("Student deleted successfully with ID: {}", id);
    }
    
    /**
     * Search students by name or department
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> searchStudents(String searchTerm) {
        log.info("Searching students with term: {}", searchTerm);
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllStudents();
        }
        
        List<Student> students = studentRepository.searchStudents(searchTerm);
        log.info("Found {} students matching search term", students.size());
        
        return students;
    }
    
    /**
     * Get students by department
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByDepartment(String department) {
        log.info("Fetching students from department: {}", department);
        
        List<Student> students = studentRepository.findByDepartment(department);
        log.info("Found {} students in department {}", students.size(), department);
        
        return students;
    }
    
    /**
     * Get students by year
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByYear(Integer year) {
        log.info("Fetching students from year: {}", year);
        
        List<Student> students = studentRepository.findByYear(year);
        log.info("Found {} students in year {}", students.size(), year);
        
        return students;
    }
    
    /**
     * Get students with pagination
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Student> getStudentsWithPagination(Pageable pageable) {
        log.info("Fetching students with pagination: page {}, size {}", 
                 pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Student> studentsPage = studentRepository.findAll(pageable);
        log.info("Retrieved page {} of {} with {} students", 
                 studentsPage.getNumber(), studentsPage.getTotalPages(), studentsPage.getNumberOfElements());
        
        return studentsPage;
    }
    
    /**
     * Search students with pagination
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Student> searchStudentsWithPagination(String searchTerm, Pageable pageable) {
        log.info("Searching students with term '{}' and pagination: page {}, size {}", 
                 searchTerm, pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Student> studentsPage = studentRepository.searchStudentsWithPagination(searchTerm, pageable);
        log.info("Found {} students matching search term", studentsPage.getTotalElements());
        
        return studentsPage;
    }
    
    /**
     * Get all unique departments
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllDepartments() {
        log.info("Fetching all departments");
        
        List<String> departments = studentRepository.findAllDepartments();
        log.info("Found {} departments", departments.size());
        
        return departments;
    }
    
    /**
     * Get student count by department
     */
    @Override
    @Transactional(readOnly = true)
    public long getStudentCountByDepartment(String department) {
        log.info("Counting students in department: {}", department);
        
        long count = studentRepository.countByDepartment(department);
        log.info("Found {} students in department {}", count, department);
        
        return count;
    }
}
