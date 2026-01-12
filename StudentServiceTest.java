package com.placement.studentms.service;

import com.placement.studentms.dto.StudentDTO;
import com.placement.studentms.exception.DuplicateResourceException;
import com.placement.studentms.exception.ResourceNotFoundException;
import com.placement.studentms.model.Student;
import com.placement.studentms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for StudentService
 * 
 * Testing Framework: JUnit 5 + Mockito
 * 
 * Interview Tip: Unit tests should:
 * 1. Test one thing at a time
 * 2. Be independent of each other
 * 3. Use mocks to isolate the unit being tested
 * 4. Follow Arrange-Act-Assert pattern
 * 
 * @ExtendWith(MockitoExtension.class) - Enables Mockito annotations
 * @Mock - Creates a mock object
 * @InjectMocks - Creates instance and injects mocks into it
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO testStudentDTO;
    private Student testStudent;

    /**
     * Setup test data before each test
     * @BeforeEach runs before each @Test method
     */
    @BeforeEach
    void setUp() {
        testStudentDTO = new StudentDTO();
        testStudentDTO.setName("Test Student");
        testStudentDTO.setEmail("test@example.com");
        testStudentDTO.setDepartment("Computer Science");
        testStudentDTO.setYear(3);
        testStudentDTO.setPhoneNumber("9876543210");

        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Test Student");
        testStudent.setEmail("test@example.com");
        testStudent.setDepartment("Computer Science");
        testStudent.setYear(3);
        testStudent.setPhoneNumber("9876543210");
    }

    /**
     * Test: Create Student - Success Case
     * 
     * Arrange: Setup test data and mock behavior
     * Act: Call the method being tested
     * Assert: Verify the result
     */
    @Test
    void createStudent_Success() {
        // Arrange
        when(studentRepository.existsByEmail(testStudentDTO.getEmail())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // Act
        StudentDTO result = studentService.createStudent(testStudentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testStudentDTO.getName(), result.getName());
        assertEquals(testStudentDTO.getEmail(), result.getEmail());
        
        // Verify repository methods were called
        verify(studentRepository, times(1)).existsByEmail(testStudentDTO.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    /**
     * Test: Create Student - Duplicate Email
     * Should throw DuplicateResourceException
     */
    @Test
    void createStudent_DuplicateEmail_ThrowsException() {
        // Arrange
        when(studentRepository.existsByEmail(testStudentDTO.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            studentService.createStudent(testStudentDTO);
        });

        // Verify save was never called
        verify(studentRepository, never()).save(any(Student.class));
    }

    /**
     * Test: Get All Students - Success
     */
    @Test
    void getAllStudents_Success() {
        // Arrange
        List<Student> students = Arrays.asList(testStudent);
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<StudentDTO> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testStudent.getName(), result.get(0).getName());
        
        verify(studentRepository, times(1)).findAll();
    }

    /**
     * Test: Get Student by ID - Success
     */
    @Test
    void getStudentById_Success() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // Act
        StudentDTO result = studentService.getStudentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testStudent.getName(), result.getName());
        assertEquals(testStudent.getEmail(), result.getEmail());
        
        verify(studentRepository, times(1)).findById(1L);
    }

    /**
     * Test: Get Student by ID - Not Found
     * Should throw ResourceNotFoundException
     */
    @Test
    void getStudentById_NotFound_ThrowsException() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(999L);
        });
        
        verify(studentRepository, times(1)).findById(999L);
    }

    /**
     * Test: Update Student - Success
     */
    @Test
    void updateStudent_Success() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        StudentDTO updateDTO = new StudentDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setEmail("test@example.com"); // Same email
        updateDTO.setDepartment("Electronics");
        updateDTO.setYear(4);
        updateDTO.setPhoneNumber("9876543211");

        // Act
        StudentDTO result = studentService.updateStudent(1L, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    /**
     * Test: Delete Student - Success
     */
    @Test
    void deleteStudent_Success() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> {
            studentService.deleteStudent(1L);
        });

        // Assert
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    /**
     * Test: Delete Student - Not Found
     */
    @Test
    void deleteStudent_NotFound_ThrowsException() {
        // Arrange
        when(studentRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });
        
        verify(studentRepository, times(1)).existsById(999L);
        verify(studentRepository, never()).deleteById(999L);
    }

    /**
     * Test: Search Students
     */
    @Test
    void searchStudents_Success() {
        // Arrange
        List<Student> students = Arrays.asList(testStudent);
        when(studentRepository.searchByNameOrDepartment("test")).thenReturn(students);

        // Act
        List<StudentDTO> result = studentService.searchStudents("test");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        
        verify(studentRepository, times(1)).searchByNameOrDepartment("test");
    }
}

/**
 * INTERVIEW QUESTIONS ON TESTING:
 * 
 * Q1: What is the difference between @Mock and @InjectMocks?
 * A: @Mock creates a mock object (dependency)
 *    @InjectMocks creates the object being tested and injects mocks into it
 * 
 * Q2: Why use Mockito?
 * A: - Isolates unit being tested
 *    - Avoids database/network calls in tests
 *    - Makes tests faster
 *    - Allows testing edge cases easily
 * 
 * Q3: What is the Arrange-Act-Assert pattern?
 * A: - Arrange: Setup test data and mocks
 *    - Act: Execute the method being tested
 *    - Assert: Verify the result
 * 
 * Q4: What is verify() used for?
 * A: Verifies that a mock method was called with specific parameters
 *    Example: verify(repository, times(1)).save(any(Student.class))
 * 
 * Q5: What is the difference between unit tests and integration tests?
 * A: - Unit tests: Test individual components in isolation (with mocks)
 *    - Integration tests: Test multiple components together (with real database)
 * 
 * Q6: What is test coverage?
 * A: Percentage of code covered by tests
 *    Aim for 80%+ coverage for critical business logic
 * 
 * Q7: What is @BeforeEach?
 * A: Runs before each test method
 *    Used to setup common test data
 *    Alternative: @BeforeAll (runs once before all tests)
 * 
 * Q8: How would you test a REST controller?
 * A: Use @WebMvcTest and MockMvc
 *    Example: mockMvc.perform(get("/api/students"))
 *                    .andExpect(status().isOk())
 */
