# 🚀 Quick Start Guide

## 🎯 **Fastest Way to Get Started**

### **Option 1: Local Development (Recommended)**

1. **Build Backend:**
```bash
BUILD-BACKEND.bat
```

2. **Start Services:**
```bash
RUN-LOCAL.bat
```

3. **Start Frontend:**
```bash
cd ecommerce_frontend
START-FRONTEND.bat
```

### **Option 2: Full Docker (If Local Fails)**

1. **Start Everything:**
```bash
RUN-DOCKER.bat
```

## 🔧 **If You Get Compilation Errors:**

The build might fail due to missing Elasticsearch dependencies. Here's the fix:

### **Quick Fix:**
1. Run `BUILD-BACKEND.bat` first
2. If it fails, the Elasticsearch files have been simplified
3. Try `RUN-LOCAL.bat` instead of Docker

### **Manual Fix:**
```bash
# Clean everything
mvn clean

# Build without tests
mvn compile -DskipTests

# If successful, package
mvn package -DskipTests
```

## 📱 **Access URLs:**

- **Backend API**: http://localhost:8080
- **Frontend**: http://localhost:3000
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/health

## 🐛 **Troubleshooting:**

### **Port Already in Use:**
```bash
# Kill processes on ports
npx kill-port 3000 8080 3306 6379
```

### **MySQL Connection Issues:**
```bash
# Reset MySQL container
docker stop ecommerce-mysql
docker rm ecommerce-mysql
# Then run RUN-LOCAL.bat again
```

### **Build Failures:**
1. Make sure Java 17+ is installed
2. Make sure Maven is installed
3. Try `BUILD-BACKEND.bat` first
4. If Docker fails, use `RUN-LOCAL.bat`

## ✅ **Success Indicators:**

- Backend: You see "Started EcommerceApplication" in logs
- Frontend: Browser opens to http://localhost:3000
- Database: Can register/login users
- API: Swagger UI loads at http://localhost:8080/swagger-ui.html

**Choose the method that works best for your setup!** 🎯