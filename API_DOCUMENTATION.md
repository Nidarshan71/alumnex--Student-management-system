# API DOCUMENTATION
# Student Management System REST API

---

## Base URL
```
http://localhost:8080/api/students
```

---

## Authentication
Currently, the API uses basic authentication (development mode).
- Username: `admin`
- Password: `admin123`

For production, implement JWT-based authentication.

---

## API Endpoints

### 1. GET ALL STUDENTS
**Endpoint:** `GET /api/students`

**Description:** Retrieve all students from the database

**Request:**
```http
GET http://localhost:8080/api/students
```

**Response:** `200 OK`
```json
[
  {
    "studentId": 1,
    "name": "Rajesh Kumar",
    "email": "rajesh.kumar@example.com",
    "department": "Computer Science",
    "year": 3,
    "phoneNumber": "9876543210",
    "createdAt": "2025-01-07T10:30:00",
    "updatedAt": "2025-01-07T10:30:00"
  },
  {
    "studentId": 2,
    "name": "Priya Sharma",
    "email": "priya.sharma@example.com",
    "department": "Electronics",
    "year": 2,
    "phoneNumber": "9876543211",
    "createdAt": "2025-01-07T10:35:00",
    "updatedAt": "2025-01-07T10:35:00"
  }
]
```

---

### 2. GET STUDENT BY ID
**Endpoint:** `GET /api/students/{id}`

**Description:** Retrieve a specific student by ID

**Request:**
```http
GET http://localhost:8080/api/students/1
```

**Response:** `200 OK`
```json
{
  "studentId": 1,
  "name": "Rajesh Kumar",
  "email": "rajesh.kumar@example.com",
  "department": "Computer Science",
  "year": 3,
  "phoneNumber": "9876543210",
  "createdAt": "2025-01-07T10:30:00",
  "updatedAt": "2025-01-07T10:30:00"
}
```

**Error Response:** `404 NOT FOUND`
```json
{
  "timestamp": "2025-01-07T10:45:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with ID: 1",
  "path": "/api/students/1"
}
```

---

### 3. CREATE NEW STUDENT
**Endpoint:** `POST /api/students`

**Description:** Create a new student record

**Request:**
```http
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "name": "Amit Patel",
  "email": "amit.patel@example.com",
  "department": "Mechanical",
  "year": 4,
  "phoneNumber": "9876543212"
}
```

**Validation Rules:**
- `name`: Required, 2-100 characters
- `email`: Required, valid email format, must be unique
- `department`: Required, 2-50 characters
- `year`: Required, integer between 1-4
- `phoneNumber`: Required, exactly 10 digits

**Response:** `201 CREATED`
```json
{
  "studentId": 3,
  "name": "Amit Patel",
  "email": "amit.patel@example.com",
  "department": "Mechanical",
  "year": 4,
  "phoneNumber": "9876543212",
  "createdAt": "2025-01-07T11:00:00",
  "updatedAt": "2025-01-07T11:00:00"
}
```

**Error Response:** `400 BAD REQUEST` (Validation Failed)
```json
{
  "timestamp": "2025-01-07T11:00:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "fieldErrors": {
    "name": "Name is required",
    "phoneNumber": "Phone number must be 10 digits"
  },
  "path": "/api/students"
}
```

**Error Response:** `409 CONFLICT` (Duplicate Email)
```json
{
  "timestamp": "2025-01-07T11:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Student with email amit.patel@example.com already exists",
  "path": "/api/students"
}
```

---

### 4. UPDATE STUDENT
**Endpoint:** `PUT /api/students/{id}`

**Description:** Update an existing student's information

**Request:**
```http
PUT http://localhost:8080/api/students/3
Content-Type: application/json

{
  "name": "Amit Kumar Patel",
  "email": "amit.patel@example.com",
  "department": "Mechanical",
  "year": 4,
  "phoneNumber": "9876543212"
}
```

**Response:** `200 OK`
```json
{
  "studentId": 3,
  "name": "Amit Kumar Patel",
  "email": "amit.patel@example.com",
  "department": "Mechanical",
  "year": 4,
  "phoneNumber": "9876543212",
  "createdAt": "2025-01-07T11:00:00",
  "updatedAt": "2025-01-07T11:15:00"
}
```

**Error Response:** `404 NOT FOUND`
```json
{
  "timestamp": "2025-01-07T11:15:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with ID: 3",
  "path": "/api/students/3"
}
```

---

### 5. DELETE STUDENT
**Endpoint:** `DELETE /api/students/{id}`

**Description:** Delete a student from the database

**Request:**
```http
DELETE http://localhost:8080/api/students/3
```

**Response:** `200 OK`
```json
{
  "message": "Student deleted successfully",
  "studentId": "3"
}
```

**Error Response:** `404 NOT FOUND`
```json
{
  "timestamp": "2025-01-07T11:20:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with ID: 3",
  "path": "/api/students/3"
}
```

---

### 6. SEARCH STUDENTS
**Endpoint:** `GET /api/students/search?q={searchTerm}`

**Description:** Search students by name, email, or department

**Request:**
```http
GET http://localhost:8080/api/students/search?q=rajesh
```

**Response:** `200 OK`
```json
[
  {
    "studentId": 1,
    "name": "Rajesh Kumar",
    "email": "rajesh.kumar@example.com",
    "department": "Computer Science",
    "year": 3,
    "phoneNumber": "9876543210",
    "createdAt": "2025-01-07T10:30:00",
    "updatedAt": "2025-01-07T10:30:00"
  }
]
```

---

### 7. GET STUDENTS BY DEPARTMENT
**Endpoint:** `GET /api/students/department/{department}`

**Description:** Get all students in a specific department

**Request:**
```http
GET http://localhost:8080/api/students/department/Computer Science
```

**Response:** `200 OK`
```json
[
  {
    "studentId": 1,
    "name": "Rajesh Kumar",
    "email": "rajesh.kumar@example.com",
    "department": "Computer Science",
    "year": 3,
    "phoneNumber": "9876543210",
    "createdAt": "2025-01-07T10:30:00",
    "updatedAt": "2025-01-07T10:30:00"
  }
]
```

---

### 8. GET STUDENTS BY YEAR
**Endpoint:** `GET /api/students/year/{year}`

**Description:** Get all students in a specific academic year

**Request:**
```http
GET http://localhost:8080/api/students/year/3
```

**Response:** `200 OK`
```json
[
  {
    "studentId": 1,
    "name": "Rajesh Kumar",
    "email": "rajesh.kumar@example.com",
    "department": "Computer Science",
    "year": 3,
    "phoneNumber": "9876543210",
    "createdAt": "2025-01-07T10:30:00",
    "updatedAt": "2025-01-07T10:30:00"
  }
]
```

---

### 9. GET STUDENTS WITH PAGINATION
**Endpoint:** `GET /api/students/paginated?page={page}&size={size}&sortBy={field}&direction={direction}`

**Description:** Get paginated list of students with sorting

**Query Parameters:**
- `page`: Page number (default: 0)
- `size`: Items per page (default: 10)
- `sortBy`: Field to sort by (default: studentId)
- `direction`: ASC or DESC (default: ASC)

**Request:**
```http
GET http://localhost:8080/api/students/paginated?page=0&size=5&sortBy=name&direction=ASC
```

**Response:** `200 OK`
```json
{
  "content": [
    {
      "studentId": 3,
      "name": "Amit Patel",
      "email": "amit.patel@example.com",
      "department": "Mechanical",
      "year": 4,
      "phoneNumber": "9876543212",
      "createdAt": "2025-01-07T11:00:00",
      "updatedAt": "2025-01-07T11:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalElements": 10,
  "totalPages": 2,
  "last": false,
  "first": true,
  "numberOfElements": 5
}
```

---

### 10. GET ALL DEPARTMENTS
**Endpoint:** `GET /api/students/departments`

**Description:** Get list of all unique departments

**Request:**
```http
GET http://localhost:8080/api/students/departments
```

**Response:** `200 OK`
```json
[
  "Civil",
  "Computer Science",
  "Electronics",
  "Mechanical"
]
```

---

### 11. GET STUDENT COUNT BY DEPARTMENT
**Endpoint:** `GET /api/students/count/department/{department}`

**Description:** Get the number of students in a specific department

**Request:**
```http
GET http://localhost:8080/api/students/count/department/Computer Science
```

**Response:** `200 OK`
```json
{
  "department": "Computer Science",
  "count": 3
}
```

---

## Error Codes

| Status Code | Description |
|-------------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request (Validation Error) |
| 404 | Not Found |
| 409 | Conflict (Duplicate Resource) |
| 500 | Internal Server Error |

---

## Testing with Postman

### 1. Import Collection
Create a new collection in Postman named "Student Management System"

### 2. Set Environment Variables
- `BASE_URL`: http://localhost:8080
- `API_PATH`: /api/students

### 3. Example Requests

**Create Student:**
```
POST {{BASE_URL}}{{API_PATH}}
Body (JSON):
{
  "name": "Test Student",
  "email": "test@example.com",
  "department": "Computer Science",
  "year": 2,
  "phoneNumber": "9876543210"
}
```

**Get All Students:**
```
GET {{BASE_URL}}{{API_PATH}}
```

**Update Student:**
```
PUT {{BASE_URL}}{{API_PATH}}/1
Body (JSON):
{
  "name": "Updated Name",
  "email": "test@example.com",
  "department": "Computer Science",
  "year": 3,
  "phoneNumber": "9876543210"
}
```

**Delete Student:**
```
DELETE {{BASE_URL}}{{API_PATH}}/1
```

---

## Testing with cURL

### Get All Students
```bash
curl -X GET http://localhost:8080/api/students
```

### Create Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Student",
    "email": "test@example.com",
    "department": "Computer Science",
    "year": 2,
    "phoneNumber": "9876543210"
  }'
```

### Update Student
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Name",
    "email": "test@example.com",
    "department": "Computer Science",
    "year": 3,
    "phoneNumber": "9876543210"
  }'
```

### Delete Student
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

---

## Notes

1. **CORS**: The API allows all origins in development mode
2. **Validation**: All fields are validated according to annotations in the Student entity
3. **Error Handling**: Global exception handler provides consistent error responses
4. **Logging**: All operations are logged for debugging
5. **Transactions**: Database operations are wrapped in transactions for data integrity

---

## Future Enhancements

1. JWT-based authentication
2. Role-based access control (RBAC)
3. File upload for student photos
4. Bulk import/export (CSV, Excel)
5. Email notifications
6. Audit logging
7. API versioning
8. Rate limiting
