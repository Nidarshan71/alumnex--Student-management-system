package com.placement.studentms.service;

import com.placement.studentms.dto.StudentDTO;
import com.placement.studentms.exception.DuplicateResourceException;
import com.placement.studentms.exception.ResourceNotFoundException;
import com.placement.studentms.model.Student;
import com.placement.studentms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Student Service Layer
 * 
 * Service Layer responsibilities:
 * 1. Business logic implementation
 * 2. Transaction management
 * 3. Data transformation (Entity <-> DTO)
 * 4. Validation and exception handling
 * 
 * @Service - Marks this as a service component
 * @RequiredArgsConstructor - Lombok generates constructor with required fields (final fields)
 * @Transactional - Ensures database operations are atomic
 * 
 * Interview Tip: Service layer sits between Controller and Repository
 * It contains the business logic and keeps controllers thin
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    // Constructor injection via Lombok @RequiredArgsConstructor
    private final StudentRepository studentRepository;

    /**
     * Create a new student
     * 
     * Steps:
     * 1. Check if email already exists (business rule)
     * 2. Convert DTO to Entity
     * 3. Save to database
     * 4. Convert Entity back to DTO and return
     */
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Business validation
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentDTO.getEmail() + " already exists");
        }

        // Convert DTO to Entity
        Student student = convertToEntity(studentDTO);
        
        // Save to database
        Student savedStudent = studentRepository.save(student);
        
        // Convert Entity back to DTO
        return convertToDTO(savedStudent);
    }

    /**
     * Get all students
     */
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get student by ID
     */
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return convertToDTO(student);
    }

    /**
     * Update student
     * 
     * Steps:
     * 1. Check if student exists
     * 2. Check if new email is duplicate (if email is being changed)
     * 3. Update fields
     * 4. Save and return
     */
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        // Check if student exists
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // Check for duplicate email if email is being changed
        if (!existingStudent.getEmail().equals(studentDTO.getEmail()) && 
            studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentDTO.getEmail() + " already exists");
        }

        // Update fields
        existingStudent.setName(studentDTO.getName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setDepartment(studentDTO.getDepartment());
        existingStudent.setYear(studentDTO.getYear());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());

        // Save updated student
        Student updatedStudent = studentRepository.save(existingStudent);
        return convertToDTO(updatedStudent);
    }

    /**
     * Delete student
     */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student", "id", id);
        }
        studentRepository.deleteById(id);
    }

    /**
     * Search students by keyword (searches in name and department)
     */
    public List<StudentDTO> searchStudents(String keyword) {
        List<Student> students = studentRepository.searchByNameOrDepartment(keyword);
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get students by department
     */
    public List<StudentDTO> getStudentsByDepartment(String department) {
        List<Student> students = studentRepository.findByDepartment(department);
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get students by year
     */
    public List<StudentDTO> getStudentsByYear(Integer year) {
        List<Student> students = studentRepository.findByYear(year);
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get students with pagination and sorting
     * 
     * @param page - Page number (0-indexed)
     * @param size - Number of records per page
     * @param sortBy - Field to sort by
     * @param direction - Sort direction (ASC or DESC)
     */
    public Page<StudentDTO> getStudentsWithPagination(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        
        return studentPage.map(this::convertToDTO);
    }

    /**
     * Search with pagination
     */
    public Page<StudentDTO> searchStudentsWithPagination(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepository.searchByNameOrDepartment(keyword, pageable);
        
        return studentPage.map(this::convertToDTO);
    }

    /**
     * Helper method: Convert Entity to DTO
     * 
     * Interview Tip: This is called "mapping" or "transformation"
     * In large projects, use libraries like ModelMapper or MapStruct
     */
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setDepartment(student.getDepartment());
        dto.setYear(student.getYear());
        dto.setPhoneNumber(student.getPhoneNumber());
        return dto;
    }

    /**
     * Helper method: Convert DTO to Entity
     */
    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());
        student.setYear(dto.getYear());
        student.setPhoneNumber(dto.getPhoneNumber());
        return student;
    }
}
