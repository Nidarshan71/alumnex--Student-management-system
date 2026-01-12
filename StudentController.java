package com.placement.sms.controller;

import com.placement.sms.model.Student;
import com.placement.sms.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentController - REST API Controller
 * 
 * Handles all HTTP requests related to student management
 * Follows REST conventions:
 * - GET: Retrieve data
 * - POST: Create new resource
 * - PUT: Update existing resource
 * - DELETE: Remove resource
 * 
 * @RestController - Combines @Controller and @ResponseBody
 * @RequestMapping - Base URL for all endpoints in this controller
 * @CrossOrigin - Enables CORS for frontend access
 * 
 * @author Student Management System
 * @version 1.0
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*") // Allow requests from any origin (for development)
@Slf4j
public class StudentController {
    
    private final StudentService studentService;
    
    /**
     * Constructor injection
     */
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * GET /api/students
     * Get all students
     * 
     * @return List of all students with 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("GET /api/students - Fetch all students");
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /api/students/{id}
     * Get student by ID
     * 
     * @param id Student ID from URL path
     * @return Student object with 200 OK status
     * @throws ResourceNotFoundException if student not found (handled by GlobalExceptionHandler)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        log.info("GET /api/students/{} - Fetch student by ID", id);
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    /**
     * POST /api/students
     * Create a new student
     * 
     * @param student Student object from request body
     * @return Created student with 201 CREATED status
     * @Valid annotation triggers validation defined in Student entity
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        log.info("POST /api/students - Create new student with email: {}", student.getEmail());
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    
    /**
     * PUT /api/students/{id}
     * Update existing student
     * 
     * @param id Student ID from URL path
     * @param student Updated student data from request body
     * @return Updated student with 200 OK status
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {
        log.info("PUT /api/students/{} - Update student", id);
        Student updatedStudent = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }
    
    /**
     * DELETE /api/students/{id}
     * Delete student
     * 
     * @param id Student ID from URL path
     * @return Success message with 200 OK status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        log.info("DELETE /api/students/{} - Delete student", id);
        studentService.deleteStudent(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        response.put("studentId", String.valueOf(id));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/students/search?q={searchTerm}
     * Search students by name or department
     * 
     * @param searchTerm Search keyword from query parameter
     * @return List of matching students with 200 OK status
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam(value = "q", required = false, defaultValue = "") String searchTerm) {
        log.info("GET /api/students/search?q={} - Search students", searchTerm);
        List<Student> students = studentService.searchStudents(searchTerm);
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /api/students/department/{department}
     * Get students by department
     * 
     * @param department Department name from URL path
     * @return List of students in the department with 200 OK status
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Student>> getStudentsByDepartment(@PathVariable String department) {
        log.info("GET /api/students/department/{} - Get students by department", department);
        List<Student> students = studentService.getStudentsByDepartment(department);
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /api/students/year/{year}
     * Get students by year
     * 
     * @param year Academic year from URL path
     * @return List of students in the year with 200 OK status
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Student>> getStudentsByYear(@PathVariable Integer year) {
        log.info("GET /api/students/year/{} - Get students by year", year);
        List<Student> students = studentService.getStudentsByYear(year);
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /api/students/paginated?page={page}&size={size}&sort={field}&direction={direction}
     * Get students with pagination and sorting
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param sortBy Field to sort by (default: studentId)
     * @param direction Sort direction (default: ASC)
     * @return Page of students with 200 OK status
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<Student>> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "studentId") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        
        log.info("GET /api/students/paginated?page={}&size={}&sort={}&direction={}", 
                 page, size, sortBy, direction);
        
        // Create sort object
        Sort sort = direction.equalsIgnoreCase("DESC") 
                    ? Sort.by(sortBy).descending() 
                    : Sort.by(sortBy).ascending();
        
        // Create pageable object
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Student> studentsPage = studentService.getStudentsWithPagination(pageable);
        return ResponseEntity.ok(studentsPage);
    }
    
    /**
     * GET /api/students/search/paginated?q={searchTerm}&page={page}&size={size}
     * Search students with pagination
     * 
     * @param searchTerm Search keyword
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of matching students with 200 OK status
     */
    @GetMapping("/search/paginated")
    public ResponseEntity<Page<Student>> searchStudentsWithPagination(
            @RequestParam(value = "q", defaultValue = "") String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("GET /api/students/search/paginated?q={}&page={}&size={}", 
                 searchTerm, page, size);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentsPage = studentService.searchStudentsWithPagination(searchTerm, pageable);
        
        return ResponseEntity.ok(studentsPage);
    }
    
    /**
     * GET /api/students/departments
     * Get all unique departments
     * 
     * @return List of department names with 200 OK status
     */
    @GetMapping("/departments")
    public ResponseEntity<List<String>> getAllDepartments() {
        log.info("GET /api/students/departments - Get all departments");
        List<String> departments = studentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    
    /**
     * GET /api/students/count/department/{department}
     * Get student count by department
     * 
     * @param department Department name
     * @return Count with 200 OK status
     */
    @GetMapping("/count/department/{department}")
    public ResponseEntity<Map<String, Object>> getStudentCountByDepartment(@PathVariable String department) {
        log.info("GET /api/students/count/department/{}", department);
        long count = studentService.getStudentCountByDepartment(department);
        
        Map<String, Object> response = new HashMap<>();
        response.put("department", department);
        response.put("count", count);
        
        return ResponseEntity.ok(response);
    }
}
