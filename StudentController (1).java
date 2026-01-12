package com.placement.studentms.controller;

import com.placement.studentms.dto.StudentDTO;
import com.placement.studentms.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Student REST Controller
 * 
 * Controller Layer responsibilities:
 * 1. Handle HTTP requests
 * 2. Validate input (using @Valid)
 * 3. Call service layer methods
 * 4. Return appropriate HTTP responses
 * 
 * @RestController - Combination of @Controller and @ResponseBody
 *   All methods return data (usually JSON) instead of views
 * @RequestMapping - Base path for all endpoints in this controller
 * @CrossOrigin - Enables CORS for frontend communication
 * 
 * Interview Tip: Controllers should be thin - delegate business logic to service layer
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*") // Allow all origins for development
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * Create a new student
     * 
     * POST /api/students
     * @Valid triggers validation on StudentDTO
     * Returns 201 CREATED on success
     */
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Get all students
     * 
     * GET /api/students
     * Returns 200 OK with list of students
     */
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * Get student by ID
     * 
     * GET /api/students/{id}
     * @PathVariable extracts {id} from URL
     * Returns 200 OK if found, 404 NOT FOUND if not (handled by GlobalExceptionHandler)
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * Update student
     * 
     * PUT /api/students/{id}
     * Returns 200 OK with updated student
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updated = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete student
     * 
     * DELETE /api/students/{id}
     * Returns 200 OK with success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Search students
     * 
     * GET /api/students/search?keyword=john
     * @RequestParam extracts query parameter
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> searchStudents(@RequestParam String keyword) {
        List<StudentDTO> students = studentService.searchStudents(keyword);
        return ResponseEntity.ok(students);
    }

    /**
     * Get students by department
     * 
     * GET /api/students/department/Computer Science
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentDTO>> getStudentsByDepartment(@PathVariable String department) {
        List<StudentDTO> students = studentService.getStudentsByDepartment(department);
        return ResponseEntity.ok(students);
    }

    /**
     * Get students by year
     * 
     * GET /api/students/year/3
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<StudentDTO>> getStudentsByYear(@PathVariable Integer year) {
        List<StudentDTO> students = studentService.getStudentsByYear(year);
        return ResponseEntity.ok(students);
    }

    /**
     * Get students with pagination and sorting
     * 
     * GET /api/students/paginated?page=0&size=10&sortBy=name&direction=asc
     * 
     * Interview Tip: Pagination is crucial for large datasets
     * Returns: Page object with content, totalPages, totalElements, etc.
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<StudentDTO>> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Page<StudentDTO> studentsPage = studentService.getStudentsWithPagination(page, size, sortBy, direction);
        return ResponseEntity.ok(studentsPage);
    }

    /**
     * Search with pagination
     * 
     * GET /api/students/search/paginated?keyword=john&page=0&size=10&sortBy=name&direction=asc
     */
    @GetMapping("/search/paginated")
    public ResponseEntity<Page<StudentDTO>> searchStudentsWithPagination(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Page<StudentDTO> studentsPage = studentService.searchStudentsWithPagination(keyword, page, size, sortBy, direction);
        return ResponseEntity.ok(studentsPage);
    }
}
