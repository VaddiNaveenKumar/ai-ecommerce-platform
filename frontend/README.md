# 🚀 AI E-commerce Frontend

React frontend for the AI-powered e-commerce platform.

## ✨ Features

- 🔐 **User Authentication** - Register & Login
- 👤 **User Profile** - View profile information
- 🎨 **Modern UI** - Clean and responsive design
- 🔄 **Real-time API** - Connected to Spring Boot backend

## 🚀 Quick Start

### Option 1: Use Batch Script
```bash
# Run this from the main project directory
START-FRONTEND.bat
```

### Option 2: Manual Setup
```bash
cd frontend
npm install
npm start
```

## 📱 Pages

- **Login** (`/login`) - User login form
- **Register** (`/register`) - User registration form  
- **Dashboard** (`/dashboard`) - User profile and features

## 🔧 Configuration

The frontend connects to the backend at:
- **Backend API**: `http://localhost:8080/api`
- **Frontend**: `http://localhost:3000`

## 🧪 Test Flow

1. **Register** a new user
2. **Login** with credentials
3. **View Dashboard** with profile info
4. **Logout** to return to login

## 📦 Dependencies

- React 18
- React Router DOM
- Axios for API calls
- Context API for state management

## 🎯 API Integration

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/users/profile` - Get user profile

Make sure your Spring Boot backend is running on port 8080!