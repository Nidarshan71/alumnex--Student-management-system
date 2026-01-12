# STUDENT MANAGEMENT SYSTEM - SETUP & DEPLOYMENT GUIDE

## 📋 TABLE OF CONTENTS
1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [Backend Setup](#backend-setup)
4. [Frontend Setup](#frontend-setup)
5. [Running the Application](#running-the-application)
6. [Testing](#testing)
7. [Troubleshooting](#troubleshooting)
8. [Project Structure](#project-structure)
9. [Interview Preparation](#interview-preparation)

---

## ✅ PREREQUISITES

### Required Software
1. **Java Development Kit (JDK) 17 or higher**
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **Maven 3.6+**
   - Download: https://maven.apache.org/download.cgi
   - Verify: `mvn -version`

3. **MySQL 8.0+**
   - Download: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql --version`

4. **IDE (Choose one)**
   - IntelliJ IDEA (Recommended)
   - Eclipse
   - VS Code with Java extensions

5. **Web Browser**
   - Chrome (Recommended for developer tools)
   - Firefox
   - Edge

6. **Postman** (For API testing)
   - Download: https://www.postman.com/downloads/

---

## 🚀 PROJECT SETUP

### 1. Download/Clone Project
```bash
# If using Git
git clone <repository-url>
cd student-management-system

# Or extract the ZIP file
unzip student-management-system.zip
cd student-management-system
```

### Project Structure
```
student-management-system/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/placement/studentms/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── model/
│   │   │   │       ├── dto/
│   │   │   │       ├── exception/
│   │   │   │       └── StudentManagementSystemApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── styles.css
│   └── js/
│       └── app.js
└── docs/
    ├── database_schema.sql
    ├── API_DOCUMENTATION.md
    └── SETUP_GUIDE.md
```

---

## 🗄️ BACKEND SETUP

### Step 1: Setup MySQL Database

#### Option A: Using MySQL Command Line
```bash
# Login to MySQL
mysql -u root -p

# Run the following commands:
```

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS student_management_db;

-- Use database
USE student_management_db;

-- Verify database created
SHOW DATABASES;
```

#### Option B: Using MySQL Workbench
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Click on "Create a new schema" button
4. Name: `student_management_db`
5. Click "Apply"

### Step 2: Run Database Schema
```bash
# Navigate to docs folder
cd docs

# Run SQL script
mysql -u root -p student_management_db < database_schema.sql

# Or manually run the script in MySQL Workbench
```

### Step 3: Configure Database Connection

Edit `backend/src/main/resources/application.properties`:

```properties
# Update these values according to your MySQL setup
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**Important:** Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL root password!

### Step 4: Install Dependencies

```bash
# Navigate to backend folder
cd backend

# Clean and install dependencies
mvn clean install

# This will:
# - Download all dependencies from Maven Central
# - Compile the code
# - Run tests
# - Package the application
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 2.5 minutes
```

### Step 5: Run Backend Application

#### Option A: Using Maven
```bash
mvn spring-boot:run
```

#### Option B: Using IDE (IntelliJ IDEA)
1. Open `backend` folder in IntelliJ IDEA
2. Wait for Maven to import dependencies
3. Find `StudentManagementSystemApplication.java`
4. Right-click → Run 'StudentManagementSystemApplication'

#### Option C: Using JAR file
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/student-management-system-1.0.0.jar
```

**Expected Console Output:**
```
========================================
Student Management System Started!
API Base URL: http://localhost:8080/api
========================================
```

### Step 6: Verify Backend is Running

**Method 1: Browser**
- Open: http://localhost:8080/api/students
- Should see JSON array (possibly empty)

**Method 2: cURL**
```bash
curl http://localhost:8080/api/students
```

**Method 3: Postman**
- Create GET request to: http://localhost:8080/api/students
- Click "Send"

---

## 🎨 FRONTEND SETUP

### Step 1: Open Frontend Files

The frontend is a simple HTML/CSS/JavaScript application. No build process required!

### Step 2: Update API URL (if needed)

Edit `frontend/js/app.js`:

```javascript
// Line 14: Update if backend is running on different port
const API_BASE_URL = 'http://localhost:8080/api';
```

### Step 3: Run Frontend

#### Option A: Using Live Server (VS Code Extension)
1. Install "Live Server" extension in VS Code
2. Open `frontend` folder in VS Code
3. Right-click on `index.html`
4. Select "Open with Live Server"
5. Browser opens automatically at http://127.0.0.1:5500

#### Option B: Using Python HTTP Server
```bash
cd frontend
python -m http.server 8000
# Open browser: http://localhost:8000
```

#### Option C: Using Node.js HTTP Server
```bash
cd frontend
npx http-server -p 8000
# Open browser: http://localhost:8000
```

#### Option D: Direct File Open
1. Navigate to `frontend` folder
2. Double-click `index.html`
3. Opens in default browser

**Note:** Some browsers may block API calls when using `file://` protocol. Use option A, B, or C for best results.

---

## ▶️ RUNNING THE APPLICATION

### Complete Startup Sequence

**Terminal 1 - Backend:**
```bash
cd backend
mvn spring-boot:run
```
Wait for: "Student Management System Started!"

**Terminal 2 - Frontend:**
```bash
cd frontend
python -m http.server 8000
```

**Browser:**
- Open: http://localhost:8000
- You should see the login page

### First Time Login

**Default Admin Credentials:**
- Username: `admin`
- Password: `admin123`

**Or Register New Admin:**
1. Click "Register here"
2. Fill in details:
   - Username: (min 4 characters)
   - Email: (valid email)
   - Password: (min 6 characters)
3. Click "Register"
4. Go back to login and use new credentials

---

## 🧪 TESTING

### 1. Manual Testing via UI

**Test Create:**
1. Login to application
2. Click "Add New Student"
3. Fill in all fields
4. Click "Save Student"
5. Verify student appears in table

**Test Read:**
1. View all students in table
2. Check if data displays correctly

**Test Update:**
1. Click "Edit" on any student
2. Modify details
3. Click "Save Student"
4. Verify changes in table

**Test Delete:**
1. Click "Delete" on any student
2. Confirm deletion
3. Verify student removed from table

**Test Search:**
1. Type in search box
2. Verify filtered results

**Test Filter:**
1. Select department filter
2. Verify only that department shows
3. Select year filter
4. Verify only that year shows

### 2. API Testing with Postman

#### Import Postman Collection
1. Open Postman
2. Click "Import"
3. Create requests using API Documentation

#### Test All Endpoints
Follow the test sequence in API_DOCUMENTATION.md

### 3. Automated Testing (Optional)

```bash
cd backend

# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

---

## 🔧 TROUBLESHOOTING

### Common Issues & Solutions

#### Issue 1: Backend won't start - Port 8080 already in use
**Solution:**
```bash
# Find process using port 8080
# Windows:
netstat -ano | findstr :8080

# Mac/Linux:
lsof -i :8080

# Kill the process or change port in application.properties
server.port=8081
```

#### Issue 2: Can't connect to MySQL
**Error:** `Communications link failure`

**Solutions:**
1. Check MySQL is running:
   ```bash
   # Windows: Check Services
   # Mac: sudo mysql.server status
   # Linux: sudo systemctl status mysql
   ```

2. Verify credentials in `application.properties`

3. Test MySQL connection:
   ```bash
   mysql -u root -p
   ```

#### Issue 3: Database doesn't exist
**Error:** `Unknown database 'student_management_db'`

**Solution:**
```sql
CREATE DATABASE student_management_db;
```

#### Issue 4: Maven build fails
**Error:** `Could not resolve dependencies`

**Solutions:**
1. Check internet connection
2. Clear Maven cache:
   ```bash
   mvn dependency:purge-local-repository
   mvn clean install
   ```

#### Issue 5: Frontend can't connect to backend
**Error:** `CORS policy: No 'Access-Control-Allow-Origin' header`

**Solution:**
- Verify `@CrossOrigin(origins = "*")` is in controllers
- Or add specific origin: `@CrossOrigin(origins = "http://localhost:8000")`

#### Issue 6: Hibernate dialect error
**Error:** `No Dialect mapping for JDBC type`

**Solution:**
Update `application.properties`:
```properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

#### Issue 7: Validation not working
**Error:** No validation errors showing

**Solution:**
- Check `spring-boot-starter-validation` in pom.xml
- Ensure `@Valid` annotation on controller parameters

---

## 📁 PROJECT STRUCTURE EXPLAINED

### Backend Architecture (Layered Architecture)

```
Controller Layer (@RestController)
    ↓ (HTTP Requests)
Service Layer (@Service)
    ↓ (Business Logic)
Repository Layer (@Repository)
    ↓ (Database Access)
Database (MySQL)
```

**Flow Example:**
1. User clicks "Add Student" in UI
2. Frontend sends POST request to `/api/students`
3. `StudentController` receives request
4. Controller validates request using `@Valid`
5. Controller calls `StudentService.createStudent()`
6. Service checks business rules (duplicate email?)
7. Service calls `StudentRepository.save()`
8. Repository interacts with database
9. Data flows back up through layers
10. Controller returns JSON response
11. Frontend displays success message

### Key Components

**1. Models/Entities:**
- Represent database tables
- Use JPA annotations (@Entity, @Table)
- Example: Student.java, AdminUser.java

**2. DTOs (Data Transfer Objects):**
- API contract with frontend
- Separate from entities for security
- Example: StudentDTO.java

**3. Repositories:**
- Data access layer
- Extends JpaRepository
- Automatic CRUD methods
- Custom queries with @Query

**4. Services:**
- Business logic layer
- Transaction management
- Connects controller to repository

**5. Controllers:**
- Handle HTTP requests
- Route endpoints
- Return responses

**6. Exception Handlers:**
- Global error handling
- Consistent error responses
- Better debugging

---

## 📚 INTERVIEW PREPARATION

### Project Walkthrough Script

**"Tell me about your project":**

"I built a full-stack Student Management System using Spring Boot for the backend and HTML/CSS/JavaScript for the frontend. The system allows admins to manage student records with full CRUD operations.

On the backend, I implemented a layered architecture with clear separation of concerns - Controllers handle HTTP requests, Services contain business logic, and Repositories manage database access using Spring Data JPA.

I used MySQL for data persistence and implemented features like search, filtering, pagination, and sorting. For security, I added admin authentication and input validation using Bean Validation annotations.

The frontend is responsive and communicates with the backend via RESTful APIs using the Fetch API. I implemented real-time search with debouncing for performance and added toast notifications for user feedback.

I followed industry best practices like:
- DTO pattern for API security
- Global exception handling
- RESTful API design
- Repository pattern
- Input validation
- Responsive UI design"

### Common Interview Questions

**Q1: Why use Spring Boot?**
**A:** Spring Boot simplifies Spring configuration with auto-configuration, embedded server (Tomcat), starter dependencies, and production-ready features like health checks and metrics.

**Q2: Explain the flow when a user adds a student.**
**A:** 
1. User fills form in frontend
2. JavaScript sends POST request with JSON data
3. Spring Boot controller receives request
4. @Valid triggers Bean Validation
5. If valid, controller calls service layer
6. Service checks business rules (duplicate email)
7. Service calls repository to save
8. Repository uses JPA/Hibernate to insert into MySQL
9. Success response flows back to frontend
10. UI updates with new student

**Q3: What design patterns did you use?**
**A:**
- MVC (Model-View-Controller)
- Repository Pattern (data access abstraction)
- DTO Pattern (API security and flexibility)
- Singleton (Spring beans)
- Factory (Spring creates bean instances)

**Q4: How did you handle errors?**
**A:** 
- Used @RestControllerAdvice for global exception handling
- Created custom exceptions (ResourceNotFoundException, DuplicateResourceException)
- Returned consistent error responses with status codes, messages, and timestamps
- Validated input using Bean Validation annotations

**Q5: How does Spring Data JPA work?**
**A:** 
- Interface-based programming
- Extends JpaRepository<Entity, ID>
- Spring creates implementation at runtime
- Provides CRUD methods out of the box
- Custom queries via method naming or @Query
- Translates to SQL using Hibernate

**Q6: Explain your database design.**
**A:**
- Two tables: students and admin_users
- Used BIGINT for IDs (scalability)
- Unique constraints on email (data integrity)
- Indexes on frequently searched fields (performance)
- Audit fields (created_at, updated_at) for tracking
- CHECK constraint on year field (1-4 validation)

**Q7: What would you improve for production?**
**A:**
1. Add Spring Security with JWT tokens
2. Implement BCrypt password encryption
3. Add role-based access control
4. Implement API rate limiting
5. Add comprehensive logging
6. Use Docker for deployment
7. Add unit and integration tests
8. Implement CI/CD pipeline
9. Add caching (Redis)
10. Use HTTPS/SSL

---

## 🎯 SUCCESS CRITERIA

Your setup is successful if:

✅ Backend starts without errors
✅ MySQL connection successful
✅ Can access http://localhost:8080/api/students
✅ Frontend loads without console errors
✅ Can register/login as admin
✅ Can perform all CRUD operations
✅ Search and filtering works
✅ All API endpoints respond correctly in Postman

---

## 📞 NEED HELP?

### Debugging Checklist
1. Check all services are running (MySQL, Backend)
2. Verify correct ports (8080 for backend)
3. Check MySQL credentials in application.properties
4. Ensure database exists and has tables
5. Check browser console for frontend errors
6. Verify API URL in frontend JavaScript
7. Test backend independently with Postman
8. Check firewall/antivirus settings

### Console Logs to Check

**Backend logs should show:**
```
Started StudentManagementSystemApplication
Tomcat started on port(s): 8080
```

**Frontend console (F12) should NOT show:**
- CORS errors
- Network errors
- 404 Not Found
- Connection refused

---

## 🚀 DEPLOYMENT (Bonus)

### Deploy Backend to Heroku
```bash
# Create Heroku app
heroku create student-mgmt-api

# Add MySQL addon
heroku addons:create cleardb:ignite

# Get database URL
heroku config:get CLEARDB_DATABASE_URL

# Update application.properties with Heroku database
# Deploy
git push heroku main
```

### Deploy Frontend to Netlify
1. Push frontend to GitHub repository
2. Connect Netlify to repository
3. Update API_BASE_URL to Heroku backend URL
4. Deploy!

---

## 📝 FINAL NOTES

This project demonstrates:
- ✅ Full-stack development skills
- ✅ RESTful API design
- ✅ Database design and management
- ✅ Frontend-backend integration
- ✅ Error handling
- ✅ Input validation
- ✅ Clean code practices
- ✅ Documentation skills

**Perfect for placement interviews!**

Good luck! 🎓
