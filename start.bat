@echo off
echo Starting E-commerce Platform...
docker-compose up --build -d
echo.
echo Services starting...
timeout /t 30
echo.
echo Frontend: http://localhost:3000
echo Backend:  http://localhost:8080
echo.
pause