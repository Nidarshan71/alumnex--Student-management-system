[README.md](https://github.com/user-attachments/files/24556667/README.md)
# Student Management System
### Full-Stack Web Application for Placement Preparation

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A professional, production-grade Student Management System built with Spring Boot (Java) backend, MySQL database, and vanilla HTML/CSS/JavaScript frontend. Designed for learning and placement preparation.

---

## 🚀 Features

### Core Functionality
- ✅ **Complete CRUD Operations** - Create, Read, Update, Delete student records
- 🔍 **Advanced Search** - Search by name, email, or department
- 🎯 **Smart Filtering** - Filter by department and academic year
- 📊 **Pagination & Sorting** - Handle large datasets efficiently
- 📱 **Responsive Design** - Works on desktop, tablet, and mobile
- ✨ **Real-time Updates** - Instant UI updates after operations
- 🎨 **Modern UI/UX** - Professional, intuitive interface

### Technical Features
- 🏗️ **Three-Layer Architecture** - Controller, Service, Repository
- 🔐 **Input Validation** - Server-side and client-side validation
- ⚠️ **Global Exception Handling** - Consistent error responses
- 🔄 **RESTful API** - Industry-standard REST endpoints
- 📝 **Comprehensive Logging** - Debug and track operations
- 🎯 **Transaction Management** - ACID compliance
- 🌐 **CORS Enabled** - Cross-origin resource sharing

---

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Database**: MySQL 8.0+
- **Security**: Spring Security

### Frontend
- **HTML5** - Semantic markup
- **CSS3** - Modern styling with CSS Grid & Flexbox
- **JavaScript (ES6+)** - Async/await, Fetch API
- **Fonts**: JetBrains Mono, Instrument Serif

### Tools & Libraries
- **Lombok** - Reduces boilerplate code
- **Spring Boot DevTools** - Hot reload
- **MySQL Connector** - Database driver
- **Postman** - API testing

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   java -version
   ```

2. **Apache Maven 3.6+**
   ```bash
   mvn -version
   ```

3. **MySQL Server 8.0+**
   ```bash
   mysql --version
   ```

4. **IDE** (Choose one)
   - IntelliJ IDEA (Recommended)
   - Eclipse
   - VS Code with Java extensions

5. **Web Browser**
   - Chrome, Firefox, Edge, or Safari

6. **Optional Tools**
   - Postman (for API testing)
   - Git (for version control)

---

## 🔧 Installation & Setup

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/student-management-system.git
cd student-management-system
```

### Step 2: Database Setup

#### 2.1 Create Database
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE student_management_db;

# Exit MySQL
exit;
```

#### 2.2 Run Database Schema
```bash
mysql -u root -p student_management_db < database/schema.sql
```

#### 2.3 Update Database Credentials
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### Step 3: Backend Setup

#### 3.1 Navigate to Backend Directory
```bash
cd backend
```

#### 3.2 Install Dependencies
```bash
mvn clean install
```

#### 3.3 Run the Application
```bash
mvn spring-boot:run
```

Or run from IDE:
- Open `StudentManagementSystemApplication.java`
- Click Run button

**Backend will start on:** `http://localhost:8080`

### Step 4: Frontend Setup

#### 4.1 Open Frontend
Navigate to the `frontend` directory and open `index.html` in your browser.

**Recommended: Use a local server**
```bash
# Using Python 3
cd frontend
python -m http.server 5500

# Or use VS Code Live Server extension
```

**Frontend will be available at:** `http://localhost:5500`

### Step 5: Verify Installation

1. **Check Backend**: Visit `http://localhost:8080/api/students`
   - Should return JSON array (empty or with sample data)

2. **Check Frontend**: Visit `http://localhost:5500`
   - Should display the Student Management dashboard

---

## 🎯 Usage Guide

### Adding a Student
1. Click **"+ Add Student"** button
2. Fill in all required fields:
   - Name (2-100 characters)
   - Email (valid format, unique)
   - Department (select from dropdown)
   - Year (1-4)
   - Phone Number (exactly 10 digits)
3. Click **"Add Student"**

### Searching Students
- Type in the search box to search by:
  - Name
  - Email
  - Department
- Results update in real-time

### Filtering Students
- **Department Filter**: Select specific department
- **Year Filter**: Select academic year (1-4)
- Filters can be combined

### Editing a Student
1. Click **"Edit"** button on student row
2. Modify fields in the modal
3. Click **"Update Student"**

### Deleting a Student
1. Click **"Delete"** button on student row
2. Confirm the deletion
3. Student is removed from database

### Sorting
- Use the **"Sort by"** dropdown to sort by:
  - ID
  - Name
  - Department
  - Year
  - Date Created

### Pagination
- Navigate through pages using pagination controls
- Default: 10 students per page

---

## 📁 Project Structure

```
student-management-system/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/placement/sms/
│   │   │   │   ├── controller/
│   │   │   │   │   └── StudentController.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── StudentService.java
│   │   │   │   │   └── StudentServiceImpl.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── StudentRepository.java
│   │   │   │   ├── model/
│   │   │   │   │   └── Student.java
│   │   │   │   ├── dto/
│   │   │   │   │   └── ErrorResponse.java
│   │   │   │   ├── exception/
│   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │   ├── config/
│   │   │   │   │   ├── WebConfig.java
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   └── StudentManagementSystemApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── styles.css
│   └── js/
│       └── app.js
│
├── database/
│   └── schema.sql
│
├── docs/
│   ├── API_DOCUMENTATION.md
│   ├── INTERVIEW_GUIDE.md
│   └── PROJECT_SETUP.md
│
└── README.md
```

---

## 🔌 API Endpoints

### Base URL: `http://localhost:8080/api/students`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create new student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/api/students/search?q={term}` | Search students |
| GET | `/api/students/department/{dept}` | Get by department |
| GET | `/api/students/year/{year}` | Get by year |
| GET | `/api/students/departments` | Get all departments |
| GET | `/api/students/paginated` | Get paginated list |

**Full API Documentation**: See `docs/API_DOCUMENTATION.md`

---

## 🧪 Testing

### Using Postman

1. **Import Collection**
   - Open Postman
   - Import the provided collection
   - Set base URL variable

2. **Test Endpoints**
   ```
   GET http://localhost:8080/api/students
   POST http://localhost:8080/api/students
   PUT http://localhost:8080/api/students/1
   DELETE http://localhost:8080/api/students/1
   ```

### Using cURL

```bash
# Get all students
curl http://localhost:8080/api/students

# Create student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "department": "Computer Science",
    "year": 2,
    "phoneNumber": "9876543210"
  }'

# Update student
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "email": "john@example.com",
    "department": "Computer Science",
    "year": 3,
    "phoneNumber": "9876543210"
  }'

# Delete student
curl -X DELETE http://localhost:8080/api/students/1
```

---

## 🐛 Troubleshooting

### Common Issues

#### 1. Backend won't start
```
Error: Port 8080 is already in use
Solution: Change port in application.properties
server.port=8081
```

#### 2. Database connection failed
```
Error: Access denied for user
Solution: Check MySQL credentials in application.properties
```

#### 3. CORS errors in frontend
```
Error: CORS policy blocked
Solution: Ensure WebConfig.java is properly configured
```

#### 4. 404 errors on API calls
```
Error: Cannot GET /api/students
Solution: Verify backend is running on port 8080
```

#### 5. Frontend not updating
```
Issue: Changes not reflecting
Solution: Clear browser cache (Ctrl+Shift+R)
```

---

## 📚 Learning Resources

### Spring Boot
- [Official Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Boot Tutorial - Baeldung](https://www.baeldung.com/spring-boot)

### JPA/Hibernate
- [JPA Tutorial](https://www.tutorialspoint.com/jpa/index.htm)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)

### REST API
- [RESTful API Design Best Practices](https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/)

### JavaScript
- [MDN Web Docs](https://developer.mozilla.org/en-US/)
- [JavaScript.info](https://javascript.info/)

---

## 🎓 Interview Preparation

This project is designed to help you prepare for technical interviews. Key topics covered:

1. **Full-Stack Development**
2. **Spring Boot Framework**
3. **REST API Design**
4. **Database Design & SQL**
5. **ORM (JPA/Hibernate)**
6. **Exception Handling**
7. **Frontend Development**
8. **CRUD Operations**
9. **Authentication & Security**
10. **Version Control (Git)**

**Complete Interview Guide**: See `docs/INTERVIEW_GUIDE.md`

---

## 🔮 Future Enhancements

- [ ] JWT-based authentication
- [ ] Role-based access control (Admin, User)
- [ ] Student photo upload
- [ ] Bulk import/export (CSV, Excel)
- [ ] Email notifications
- [ ] Advanced analytics dashboard
- [ ] Unit and integration tests
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] API rate limiting

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your Name](https://linkedin.com/in/yourprofile)
- Email: your.email@example.com

---

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Stack Overflow community for problem-solving
- All open-source contributors

---

## 📞 Support

If you have any questions or need help, please:
1. Check the documentation in the `docs/` folder
2. Open an issue on GitHub
3. Contact via email

---

**⭐ If you found this project helpful, please give it a star!**

---

**Built with ❤️ for learning and placement preparation**
