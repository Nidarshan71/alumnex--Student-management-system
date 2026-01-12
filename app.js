/**
 * STUDENT MANAGEMENT SYSTEM - JavaScript
 * 
 * This file handles all frontend logic including:
 * - API communication with backend
 * - CRUD operations (Create, Read, Update, Delete)
 * - Search and filter functionality
 * - Pagination and sorting
 * - Form handling and validation
 * - UI updates and notifications
 * 
 * @author Student Management System
 * @version 1.0
 */

// ===== CONFIGURATION =====
const API_BASE_URL = 'http://localhost:8080/api/students';

// Global state management
let allStudents = [];
let filteredStudents = [];
let currentEditId = null;
let currentPage = 1;
const itemsPerPage = 10;

// ===== INITIALIZATION =====
/**
 * Initialize the application when DOM is loaded
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log('Student Management System - Initializing...');
    loadStudents();
    loadDepartments();
});

// ===== API FUNCTIONS =====

/**
 * Fetch all students from the API
 */
async function loadStudents() {
    showLoading();
    try {
        const response = await fetch(API_BASE_URL);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        allStudents = await response.json();
        filteredStudents = [...allStudents];
        
        console.log(`Loaded ${allStudents.length} students`);
        
        updateStats();
        displayStudents();
        hideLoading();
        
    } catch (error) {
        console.error('Error loading students:', error);
        showToast('Failed to load students. Please check if the backend is running.', 'error');
        hideLoading();
    }
}

/**
 * Fetch student by ID
 */
async function getStudentById(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error fetching student:', error);
        showToast('Failed to fetch student details', 'error');
        return null;
    }
}

/**
 * Create a new student
 */
async function createStudent(studentData) {
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData)
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to create student');
        }
        
        const newStudent = await response.json();
        console.log('Student created:', newStudent);
        
        showToast('Student added successfully!', 'success');
        loadStudents();
        closeModal();
        
    } catch (error) {
        console.error('Error creating student:', error);
        showToast(error.message || 'Failed to create student', 'error');
    }
}

/**
 * Update existing student
 */
async function updateStudent(id, studentData) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData)
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to update student');
        }
        
        const updatedStudent = await response.json();
        console.log('Student updated:', updatedStudent);
        
        showToast('Student updated successfully!', 'success');
        loadStudents();
        closeModal();
        
    } catch (error) {
        console.error('Error updating student:', error);
        showToast(error.message || 'Failed to update student', 'error');
    }
}

/**
 * Delete student
 */
async function deleteStudent(id) {
    if (!confirm('Are you sure you want to delete this student? This action cannot be undone.')) {
        return;
    }
    
    showLoading();
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        console.log('Student deleted:', id);
        showToast('Student deleted successfully!', 'success');
        loadStudents();
        
    } catch (error) {
        console.error('Error deleting student:', error);
        showToast('Failed to delete student', 'error');
        hideLoading();
    }
}

/**
 * Load unique departments for filter dropdown
 */
async function loadDepartments() {
    try {
        const response = await fetch(`${API_BASE_URL}/departments`);
        
        if (response.ok) {
            const departments = await response.json();
            const select = document.getElementById('departmentFilter');
            
            departments.forEach(dept => {
                const option = document.createElement('option');
                option.value = dept;
                option.textContent = dept;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading departments:', error);
    }
}

// ===== UI FUNCTIONS =====

/**
 * Display students in the table
 */
function displayStudents() {
    const tbody = document.getElementById('studentsTableBody');
    tbody.innerHTML = '';
    
    if (filteredStudents.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align: center; padding: 2rem;">
                    <p style="color: var(--color-text-muted); font-size: 1rem;">
                        No students found. Click "Add Student" to get started.
                    </p>
                </td>
            </tr>
        `;
        return;
    }
    
    // Calculate pagination
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const paginatedStudents = filteredStudents.slice(startIndex, endIndex);
    
    // Populate table
    paginatedStudents.forEach(student => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.studentId}</td>
            <td>${student.name}</td>
            <td>${student.email}</td>
            <td>${student.department}</td>
            <td>Year ${student.year}</td>
            <td>${student.phoneNumber}</td>
            <td>
                <button class="action-btn btn-edit" onclick="editStudent(${student.studentId})">
                    Edit
                </button>
                <button class="action-btn btn-delete" onclick="deleteStudent(${student.studentId})">
                    Delete
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
    
    updatePagination();
}

/**
 * Update pagination controls
 */
function updatePagination() {
    const totalPages = Math.ceil(filteredStudents.length / itemsPerPage);
    const paginationDiv = document.getElementById('pagination');
    paginationDiv.innerHTML = '';
    
    if (totalPages <= 1) return;
    
    // Previous button
    const prevBtn = document.createElement('button');
    prevBtn.className = 'page-btn';
    prevBtn.textContent = '← Previous';
    prevBtn.disabled = currentPage === 1;
    prevBtn.onclick = () => changePage(currentPage - 1);
    paginationDiv.appendChild(prevBtn);
    
    // Page numbers
    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 1 && i <= currentPage + 1)) {
            const pageBtn = document.createElement('button');
            pageBtn.className = `page-btn ${i === currentPage ? 'active' : ''}`;
            pageBtn.textContent = i;
            pageBtn.onclick = () => changePage(i);
            paginationDiv.appendChild(pageBtn);
        } else if (i === currentPage - 2 || i === currentPage + 2) {
            const dots = document.createElement('span');
            dots.textContent = '...';
            dots.style.padding = '0.5rem';
            paginationDiv.appendChild(dots);
        }
    }
    
    // Next button
    const nextBtn = document.createElement('button');
    nextBtn.className = 'page-btn';
    nextBtn.textContent = 'Next →';
    nextBtn.disabled = currentPage === totalPages;
    nextBtn.onclick = () => changePage(currentPage + 1);
    paginationDiv.appendChild(nextBtn);
}

/**
 * Change current page
 */
function changePage(page) {
    currentPage = page;
    displayStudents();
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/**
 * Update statistics dashboard
 */
function updateStats() {
    document.getElementById('totalStudents').textContent = allStudents.length;
    
    const uniqueDepts = new Set(allStudents.map(s => s.department));
    document.getElementById('totalDepartments').textContent = uniqueDepts.size;
    
    const avgYear = allStudents.length > 0 
        ? (allStudents.reduce((sum, s) => sum + s.year, 0) / allStudents.length).toFixed(1)
        : 0;
    document.getElementById('avgYear').textContent = avgYear;
    
    document.getElementById('activeStudents').textContent = filteredStudents.length;
}

// ===== SEARCH AND FILTER =====

/**
 * Handle search input
 */
function handleSearch() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    
    filteredStudents = allStudents.filter(student => {
        return (
            student.name.toLowerCase().includes(searchTerm) ||
            student.email.toLowerCase().includes(searchTerm) ||
            student.department.toLowerCase().includes(searchTerm)
        );
    });
    
    applyFilters();
}

/**
 * Handle filter changes
 */
function handleFilter() {
    const departmentFilter = document.getElementById('departmentFilter').value;
    const yearFilter = document.getElementById('yearFilter').value;
    
    filteredStudents = allStudents.filter(student => {
        const matchesDepartment = !departmentFilter || student.department === departmentFilter;
        const matchesYear = !yearFilter || student.year === parseInt(yearFilter);
        return matchesDepartment && matchesYear;
    });
    
    // Apply search term if exists
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    if (searchTerm) {
        filteredStudents = filteredStudents.filter(student => {
            return (
                student.name.toLowerCase().includes(searchTerm) ||
                student.email.toLowerCase().includes(searchTerm) ||
                student.department.toLowerCase().includes(searchTerm)
            );
        });
    }
    
    applyFilters();
}

/**
 * Apply filters and update display
 */
function applyFilters() {
    currentPage = 1;
    updateStats();
    displayStudents();
}

/**
 * Handle sorting
 */
function handleSort() {
    const sortBy = document.getElementById('sortBy').value;
    
    filteredStudents.sort((a, b) => {
        if (sortBy === 'name') {
            return a.name.localeCompare(b.name);
        } else if (sortBy === 'department') {
            return a.department.localeCompare(b.department);
        } else if (sortBy === 'year') {
            return a.year - b.year;
        } else if (sortBy === 'createdAt') {
            return new Date(b.createdAt) - new Date(a.createdAt);
        } else {
            return a.studentId - b.studentId;
        }
    });
    
    displayStudents();
}

/**
 * Reset all filters
 */
function resetFilters() {
    document.getElementById('searchInput').value = '';
    document.getElementById('departmentFilter').value = '';
    document.getElementById('yearFilter').value = '';
    document.getElementById('sortBy').value = 'studentId';
    
    filteredStudents = [...allStudents];
    currentPage = 1;
    updateStats();
    displayStudents();
}

// ===== MODAL FUNCTIONS =====

/**
 * Show modal for adding new student
 */
function showAddStudentModal() {
    currentEditId = null;
    document.getElementById('modalTitle').textContent = 'Add New Student';
    document.getElementById('submitBtn').innerHTML = '<span>Add Student</span>';
    document.getElementById('studentForm').reset();
    document.getElementById('studentModal').classList.add('active');
}

/**
 * Show modal for editing existing student
 */
async function editStudent(id) {
    currentEditId = id;
    const student = await getStudentById(id);
    
    if (!student) return;
    
    document.getElementById('modalTitle').textContent = 'Edit Student';
    document.getElementById('submitBtn').innerHTML = '<span>Update Student</span>';
    
    document.getElementById('studentName').value = student.name;
    document.getElementById('studentEmail').value = student.email;
    document.getElementById('studentDepartment').value = student.department;
    document.getElementById('studentYear').value = student.year;
    document.getElementById('studentPhone').value = student.phoneNumber;
    
    document.getElementById('studentModal').classList.add('active');
}

/**
 * Close modal
 */
function closeModal() {
    document.getElementById('studentModal').classList.remove('active');
    document.getElementById('studentForm').reset();
    currentEditId = null;
}

/**
 * Handle form submission
 */
function handleFormSubmit(event) {
    event.preventDefault();
    
    const studentData = {
        name: document.getElementById('studentName').value.trim(),
        email: document.getElementById('studentEmail').value.trim(),
        department: document.getElementById('studentDepartment').value,
        year: parseInt(document.getElementById('studentYear').value),
        phoneNumber: document.getElementById('studentPhone').value.trim()
    };
    
    // Validate phone number
    if (!/^[0-9]{10}$/.test(studentData.phoneNumber)) {
        showToast('Please enter a valid 10-digit phone number', 'error');
        return;
    }
    
    if (currentEditId) {
        updateStudent(currentEditId, studentData);
    } else {
        createStudent(studentData);
    }
}

// ===== UTILITY FUNCTIONS =====

/**
 * Show loading overlay
 */
function showLoading() {
    document.getElementById('loadingOverlay').classList.add('active');
}

/**
 * Hide loading overlay
 */
function hideLoading() {
    document.getElementById('loadingOverlay').classList.remove('active');
}

/**
 * Show toast notification
 */
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toastMessage');
    
    toastMessage.textContent = message;
    toast.className = `toast ${type}`;
    toast.classList.add('show');
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

/**
 * Refresh all data
 */
function refreshData() {
    resetFilters();
    loadStudents();
    showToast('Data refreshed successfully!', 'success');
}

/**
 * Show dashboard (reload students)
 */
function showDashboard() {
    loadStudents();
}

// ===== KEYBOARD SHORTCUTS =====
document.addEventListener('keydown', function(event) {
    // ESC to close modal
    if (event.key === 'Escape') {
        closeModal();
    }
    
    // Ctrl/Cmd + K to focus search
    if ((event.ctrlKey || event.metaKey) && event.key === 'k') {
        event.preventDefault();
        document.getElementById('searchInput').focus();
    }
});

// Close modal when clicking outside
document.getElementById('studentModal')?.addEventListener('click', function(event) {
    if (event.target === this) {
        closeModal();
    }
});

// ===== CONSOLE INFO =====
console.log('%c Student Management System ', 'background: #2563eb; color: white; padding: 10px; font-size: 16px; font-weight: bold;');
console.log('%c Developed for Placement Preparation ', 'background: #f59e0b; color: #0f172a; padding: 5px; font-size: 12px;');
console.log('%c API URL: ' + API_BASE_URL, 'color: #10b981; font-weight: bold;');
console.log('%c\n Available Commands:', 'color: #3b82f6; font-weight: bold;');
console.log('  - loadStudents()      : Reload all students');
console.log('  - refreshData()       : Refresh and reset filters');
console.log('  - showAddStudentModal(): Open add student form');
console.log('  - resetFilters()      : Clear all filters\n');
