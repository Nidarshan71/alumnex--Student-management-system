# 🚀 DEPLOYMENT GUIDE
## Host Your Student Management System Online - Get a Public URL

This guide will help you deploy your Student Management System to the internet with a public URL that anyone can access.

---

## 📋 **Deployment Overview**

We'll deploy three components:
1. **Database (MySQL)** → PlanetScale (Free)
2. **Backend (Spring Boot)** → Render.com (Free)
3. **Frontend (HTML/CSS/JS)** → Netlify (Free)

**Total Cost: $0** ✅

---

## 🗄️ **STEP 1: Deploy Database (PlanetScale)**

### 1.1 Create PlanetScale Account
1. Go to: https://planetscale.com
2. Click **"Sign up"** (use GitHub for easy signup)
3. Verify your email

### 1.2 Create Database
1. Click **"Create a new database"**
2. Name: `student-management-db`
3. Region: Choose closest to you
4. Click **"Create database"**

### 1.3 Get Connection String
1. Click on your database
2. Go to **"Connect"** tab
3. Select **"Java"** from dropdown
4. Click **"Create password"**
5. Copy the connection details:
   ```
   Host: xxxxx.us-east-1.psdb.cloud
   Username: xxxxx
   Password: xxxxx
   Database: student-management-db
   ```
6. **Save these credentials!** You'll need them later.

### 1.4 Import Schema
1. Click **"Console"** tab
2. Copy and paste the contents of `database/schema.sql`
3. Click **"Execute"**
4. Your tables are now created!

---

## 🔧 **STEP 2: Prepare Backend for Deployment**

### 2.1 Update application.properties

Create a new file: `backend/src/main/resources/application-prod.properties`

```properties
# Production Configuration
spring.application.name=student-management-system

# PlanetScale Database Configuration
spring.datasource.url=jdbc:mysql://xxxxx.us-east-1.psdb.cloud:3306/student-management-db?sslMode=VERIFY_IDENTITY
spring.datasource.username=YOUR_USERNAME_FROM_PLANETSCALE
spring.datasource.password=YOUR_PASSWORD_FROM_PLANETSCALE
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=${PORT:8080}

# Security
spring.security.user.name=admin
spring.security.user.password=${ADMIN_PASSWORD:admin123}

# Logging
logging.level.org.springframework.web=INFO
logging.level.com.placement.sms=INFO

# CORS - Update with your frontend URL after deployment
# Will be updated in Step 4
```

### 2.2 Update WebConfig.java

Open `backend/src/main/java/com/placement/sms/config/WebConfig.java` and modify:

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:5500",
                "https://your-app-name.netlify.app"  // Add after frontend deployment
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
}
```

### 2.3 Create system.properties (for Java version)

Create file: `backend/system.properties`

```properties
java.runtime.version=17
```

---

## ☁️ **STEP 3: Deploy Backend (Render.com)**

### 3.1 Prepare Repository
1. Create a GitHub account if you don't have one
2. Create a new repository: `student-management-system`
3. Upload your backend folder:
   ```bash
   cd backend
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/YOUR_USERNAME/student-management-system.git
   git push -u origin main
   ```

### 3.2 Deploy on Render
1. Go to: https://render.com
2. Click **"Sign up"** (use GitHub)
3. Click **"New +"** → **"Web Service"**
4. Connect your GitHub repository
5. Configure:
   - **Name**: `student-management-api`
   - **Region**: Choose closest to you
   - **Branch**: `main`
   - **Root Directory**: Leave empty (or `backend` if you uploaded entire project)
   - **Runtime**: `Java`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/student-management-system-1.0.0.jar --spring.profiles.active=prod`
   - **Instance Type**: `Free`

### 3.3 Add Environment Variables
In Render dashboard, add these:
1. Click **"Environment"** tab
2. Add variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   SPRING_DATASOURCE_URL=jdbc:mysql://[YOUR_PLANETSCALE_HOST]:3306/student-management-db?sslMode=VERIFY_IDENTITY
   SPRING_DATASOURCE_USERNAME=[YOUR_PLANETSCALE_USERNAME]
   SPRING_DATASOURCE_PASSWORD=[YOUR_PLANETSCALE_PASSWORD]
   ADMIN_PASSWORD=your_secure_password_here
   ```
3. Click **"Save Changes"**

### 3.4 Deploy
1. Click **"Create Web Service"**
2. Wait 5-10 minutes for build to complete
3. Your backend URL will be: `https://student-management-api.onrender.com`

### 3.5 Test Backend
Open in browser: `https://student-management-api.onrender.com/api/students`

You should see: `[]` (empty array)

✅ **Backend is live!**

---

## 🎨 **STEP 4: Deploy Frontend (Netlify)**

### 4.1 Update API URL in Frontend

Open `frontend/js/app.js` and change:

```javascript
// FROM:
const API_BASE_URL = 'http://localhost:8080/api/students';

// TO:
const API_BASE_URL = 'https://student-management-api.onrender.com/api/students';
```

### 4.2 Deploy on Netlify

**Option A: Drag and Drop (Easiest)**
1. Go to: https://netlify.com
2. Click **"Sign up"** (use GitHub)
3. Go to **"Sites"**
4. Drag and drop your `frontend` folder onto the page
5. Wait 30 seconds
6. Your site is live! 🎉

**Option B: Git Integration**
1. Push frontend to GitHub
2. In Netlify, click **"Import from Git"**
3. Select your repository
4. Configure:
   - **Publish directory**: `frontend`
   - Click **"Deploy site"**

### 4.3 Get Your URL
Your frontend URL will be: `https://random-name-12345.netlify.app`

You can customize it:
1. Go to **"Site settings"**
2. Click **"Change site name"**
3. Enter: `your-student-management-system`
4. New URL: `https://your-student-management-system.netlify.app`

### 4.4 Update CORS in Backend
1. Go back to your backend code
2. Update `WebConfig.java` with your Netlify URL
3. Push changes to GitHub
4. Render will automatically redeploy

---

## 🔒 **STEP 5: Update Backend CORS (Important!)**

Go back to `backend/src/main/java/com/placement/sms/config/WebConfig.java`:

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:5500",
                "https://your-student-management-system.netlify.app"  // Your actual Netlify URL
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
}
```

Commit and push:
```bash
git add .
git commit -m "Update CORS with production URL"
git push
```

Render will auto-redeploy in 5-10 minutes.

---

## ✅ **STEP 6: Test Your Live Application**

1. Open: `https://your-student-management-system.netlify.app`
2. Try adding a student
3. Test search, filter, edit, delete

**🎉 Your application is now live and accessible worldwide!**

---

## 📝 **Your Deployment URLs**

After completing all steps, you'll have:

```
Frontend:  https://your-student-management-system.netlify.app
Backend:   https://student-management-api.onrender.com
Database:  PlanetScale (hosted)
```

**Share your frontend URL with anyone!**

---

## 🐛 **Troubleshooting**

### Issue: Backend not connecting to database
**Solution:** Check environment variables in Render
- Make sure all PlanetScale credentials are correct
- SSL mode should be `VERIFY_IDENTITY`

### Issue: CORS errors in browser
**Solution:** 
- Verify WebConfig.java has your Netlify URL
- Wait for Render to redeploy after pushing changes
- Clear browser cache

### Issue: Frontend shows "Failed to load students"
**Solution:**
- Check if backend is running: visit backend URL + `/api/students`
- Check browser console for errors
- Verify API_BASE_URL in app.js is correct

### Issue: Render app sleeps (free tier)
**Problem:** Free tier sleeps after 15 minutes of inactivity
**Solution:** First request will take 30-60 seconds to wake up
**Alternative:** Upgrade to paid tier ($7/month) or use a ping service

---

## 💰 **Cost Breakdown**

| Service | Free Tier | Limitations |
|---------|-----------|-------------|
| PlanetScale | 5GB storage | 1 billion reads/month |
| Render | 750 hours/month | Sleeps after 15 min inactivity |
| Netlify | 100GB bandwidth | Unlimited sites |

**Total: $0/month** ✅

---

## 🚀 **Alternative Deployment Options**

### Backend Alternatives:
1. **Railway.app**
   - Similar to Render
   - $5 free credit/month
   - Easier setup

2. **Fly.io**
   - Docker-based
   - More complex but powerful
   - Free tier available

3. **AWS/Azure/GCP**
   - Professional option
   - Free tiers available
   - More complex setup

### Frontend Alternatives:
1. **Vercel**
   - Similar to Netlify
   - Great for Next.js
   - Free tier

2. **GitHub Pages**
   - Completely free
   - Good for static sites
   - Custom domain support

3. **Cloudflare Pages**
   - Free unlimited bandwidth
   - Fast global CDN

---

## 📊 **Performance Tips**

### Backend Optimization:
1. Enable caching in Spring Boot
2. Use connection pooling (already configured)
3. Add indexes to database (already done)
4. Upgrade to paid tier to prevent sleep

### Frontend Optimization:
1. Minify CSS/JS for production
2. Enable Netlify CDN (automatic)
3. Add service worker for offline support
4. Compress images

---

## 🔐 **Security Recommendations for Production**

### Before sharing publicly:

1. **Change Default Password**
   ```properties
   ADMIN_PASSWORD=use_strong_password_here
   ```

2. **Implement JWT Authentication**
   - Replace basic auth with JWT tokens
   - Add user registration/login

3. **Add Rate Limiting**
   - Prevent API abuse
   - Use Spring Boot starter for rate limiting

4. **Enable HTTPS** (automatic with Render/Netlify)

5. **Environment Variables**
   - Never commit credentials to Git
   - Use .env files locally
   - Use platform environment variables

6. **Database Security**
   - Use strong passwords
   - Enable SSL (PlanetScale default)
   - Regular backups

---

## 📱 **Share Your Project**

Once deployed, add to your:

### Resume:
```
Student Management System | Full-Stack Web Application
🔗 Live Demo: https://your-student-management-system.netlify.app
🔗 GitHub: https://github.com/yourusername/student-management-system
• Deployed full-stack application with Spring Boot, MySQL, and JavaScript
• Hosted on Render (backend), PlanetScale (database), and Netlify (frontend)
• Implemented RESTful APIs with 99.9% uptime
```

### LinkedIn:
```
🚀 Just deployed my Student Management System!

Built with:
- Spring Boot (Backend)
- MySQL (Database)  
- HTML/CSS/JavaScript (Frontend)

Features: CRUD operations, search, pagination, responsive design

Try it: [Your Netlify URL]
Code: [Your GitHub URL]

#WebDevelopment #SpringBoot #FullStack #MySQL
```

---

## 🎯 **Next Steps**

After successful deployment:

1. ✅ Test all features thoroughly
2. ✅ Add custom domain (optional)
3. ✅ Set up monitoring/analytics
4. ✅ Create demo video
5. ✅ Share on LinkedIn/Portfolio
6. ✅ Add to resume

---

## 📞 **Support Resources**

- **Render Docs**: https://render.com/docs
- **Netlify Docs**: https://docs.netlify.com
- **PlanetScale Docs**: https://planetscale.com/docs
- **Spring Boot Deployment**: https://spring.io/guides

---

## ✨ **Congratulations!**

You now have a **fully deployed, production-ready application** with a public URL that you can:
- Share with recruiters
- Add to your portfolio
- Demo in interviews
- Include in your resume

**Your application is live and accessible worldwide!** 🌍

---

**Questions? Issues? Check the troubleshooting section or platform documentation.**

**Good luck with your placements! 🚀**
