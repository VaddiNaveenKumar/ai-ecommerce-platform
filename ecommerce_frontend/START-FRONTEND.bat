@echo off
echo ========================================
echo  Starting AI E-commerce Frontend
echo ========================================

echo Checking if node_modules exists...
if not exist "node_modules" (
    echo Installing dependencies...
    npm install
) else (
    echo Dependencies already installed.
)

echo.
echo Starting development server...
npm run dev

echo.
echo ========================================
echo  Frontend URLs:
echo  🌐 Frontend: http://localhost:3000
echo  📱 Mobile view: http://localhost:3000
echo ========================================

pause