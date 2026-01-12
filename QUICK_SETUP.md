# QUICK SETUP GUIDE
## Student Management System - Get Started in 10 Minutes

---

## Prerequisites Checklist

- [ ] Java 17+ installed
- [ ] Maven 3.6+ installed
- [ ] MySQL 8.0+ installed and running
- [ ] IDE (IntelliJ IDEA recommended)
- [ ] Web browser

---

## Setup Steps

### 1️⃣ Database Setup (2 minutes)

```bash
# Step 1: Login to MySQL
mysql -u root -p

# Step 2: Create database
CREATE DATABASE student_management_db;

# Step 3: Exit
exit;

# Step 4: Import schema
mysql -u root -p student_management_db < database/schema.sql
```

### 2️⃣ Configure Backend (1 minute)

Edit `backend/src/main/resources/application.properties`:

```properties
# Update these lines with your MySQL credentials
spring.datasource.username=root
spring.datasource.password=your_password_here
```

### 3️⃣ Start Backend (3 minutes)

```bash
# Navigate to backend folder
cd backend

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

Wait for message: **"Student Management System Started Successfully!"**

Backend running at: `http://localhost:8080`

### 4️⃣ Start Frontend (2 minutes)

**Option A: Using Python**
```bash
cd frontend
python -m http.server 5500
```

**Option B: Using VS Code**
- Install "Live Server" extension
- Right-click `index.html`
- Select "Open with Live Server"

**Option C: Double-click**
- Simply open `frontend/index.html` in browser
- Note: Some features may not work without a server

Frontend running at: `http://localhost:5500`

### 5️⃣ Verify (2 minutes)

1. Open browser: `http://localhost:5500`
2. You should see the Student Management dashboard
3. Click "+ Add Student" to test

---

## Quick Test

### Test the API directly:

```bash
# Get all students (should return empty array or sample data)
curl http://localhost:8080/api/students

# Create a test student
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

---

## Common Issues & Quick Fixes

### ❌ Port 8080 already in use
**Fix:** Change port in `application.properties`
```properties
server.port=8081
```
Then update API_BASE_URL in `frontend/js/app.js`

### ❌ MySQL connection failed
**Fix:** Check MySQL is running
```bash
# Windows
net start MySQL80

# Mac/Linux
sudo systemctl start mysql
```

### ❌ Can't access frontend
**Fix:** Make sure you're using a local server, not just opening the file

### ❌ Backend won't compile
**Fix:** Make sure you're in the `backend` folder when running Maven commands

---

## Default Credentials

**MySQL:**
- Username: `root`
- Password: (your MySQL root password)

**Application:**
- Username: `admin`
- Password: `admin123`

---

## Project URLs

| Service | URL |
|---------|-----|
| Frontend | http://localhost:5500 |
| Backend API | http://localhost:8080 |
| API Endpoint | http://localhost:8080/api/students |
| MySQL | localhost:3306 |

---

## Folder Structure Quick Reference

```
student-management-system/
├── backend/          → Spring Boot application
├── frontend/         → HTML, CSS, JavaScript
├── database/         → SQL schema
├── docs/            → Documentation
└── README.md        → Full documentation
```

---

## Next Steps

1. ✅ Add some students through the UI
2. ✅ Test search and filter features
3. ✅ Try editing and deleting students
4. ✅ Review the code in your IDE
5. ✅ Read `docs/INTERVIEW_GUIDE.md` for interview prep
6. ✅ Check `docs/API_DOCUMENTATION.md` for API details

---

## Need Help?

1. **Read the full README**: `README.md`
2. **Check API docs**: `docs/API_DOCUMENTATION.md`
3. **Interview prep**: `docs/INTERVIEW_GUIDE.md`
4. **Common issues**: See troubleshooting section in README

---

## Development Commands

### Backend
```bash
# Compile
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn clean package

# Run the JAR
java -jar target/student-management-system-1.0.0.jar
```

### MySQL
```bash
# Show databases
mysql -u root -p -e "SHOW DATABASES;"

# Show tables
mysql -u root -p student_management_db -e "SHOW TABLES;"

# Count students
mysql -u root -p student_management_db -e "SELECT COUNT(*) FROM students;"

# View sample data
mysql -u root -p student_management_db -e "SELECT * FROM students LIMIT 5;"
```

---

## IDE Setup

### IntelliJ IDEA
1. File → Open → Select `backend` folder
2. Wait for Maven to download dependencies
3. Run `StudentManagementSystemApplication.java`

### VS Code
1. Install "Extension Pack for Java"
2. Install "Spring Boot Extension Pack"
3. Open `backend` folder
4. Click Run button on main class

### Eclipse
1. File → Import → Existing Maven Projects
2. Select `backend` folder
3. Right-click project → Run As → Spring Boot App

---

## Keyboard Shortcuts in UI

- `Ctrl/Cmd + K` - Focus search box
- `Esc` - Close modal
- Click outside modal - Close modal

---

## Sample Student Data

The database comes with 10 sample students. To add more:

```sql
INSERT INTO students (name, email, department, year, phone_number) VALUES
('Alice Johnson', 'alice@example.com', 'Computer Science', 2, '9876543220'),
('Bob Smith', 'bob@example.com', 'Electronics', 3, '9876543221'),
('Carol White', 'carol@example.com', 'Mechanical', 1, '9876543222');
```

---

## Success Checklist

- [ ] MySQL database created
- [ ] Backend running on port 8080
- [ ] Frontend accessible in browser
- [ ] Can view student list
- [ ] Can add new student
- [ ] Can edit student
- [ ] Can delete student
- [ ] Can search students
- [ ] Can filter by department/year
- [ ] Pagination works

---

**🎉 You're all set! Start exploring the application.**

For detailed explanations and interview preparation, see:
- `README.md` - Complete documentation
- `docs/INTERVIEW_GUIDE.md` - How to explain this project
- `docs/API_DOCUMENTATION.md` - All API endpoints

---

**Happy Coding! 🚀**
