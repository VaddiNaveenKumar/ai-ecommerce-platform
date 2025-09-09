@echo off
echo ========================================
echo  AI E-commerce Frontend - Starting...
echo ========================================

cd frontend

echo Installing dependencies...
call npm install

if %ERRORLEVEL% neq 0 (
    echo Failed to install dependencies!
    pause
    exit /b 1
)

echo Starting React development server...
echo.
echo ========================================
echo  Frontend will start at:
echo  🌐 http://localhost:3000
echo ========================================
echo  Backend should be running at:
echo  🔧 http://localhost:8080
echo ========================================
echo.

start "React Frontend" cmd /k "npm start"

echo Frontend is starting...
echo Check your browser at http://localhost:3000
pause