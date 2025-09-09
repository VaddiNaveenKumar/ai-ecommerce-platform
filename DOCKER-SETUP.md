# 🐳 Complete Docker Setup

## 🚀 **Quick Start**

### **Start Everything:**
```bash
RUN-DOCKER-COMPLETE.bat
```

### **Stop Everything:**
```bash
STOP-DOCKER.bat
```

### **View Logs:**
```bash
DOCKER-LOGS.bat
```

## 📋 **What's Included**

### **🏗️ Services:**
- **Frontend**: React + Vite + Nginx (Port 3000)
- **Backend**: Spring Boot + Java 17 (Port 8080)
- **Database**: MySQL 8.0 (Port 3306)
- **Cache**: Redis 7 (Port 6379)
- **Search**: Elasticsearch 8.11 (Port 9200)
- **Messaging**: Kafka + Zookeeper (Port 9092)

### **🌐 Access URLs:**
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/health
- **Elasticsearch**: http://localhost:9200
- **Metrics**: http://localhost:8080/actuator/prometheus

### **👤 Default Users:**
- **Admin**: `admin` / `admin123`
- **Customer**: `customer` / `customer123`

## 🔧 **Docker Commands**

### **Start Services:**
```bash
# Start all services
docker-compose up -d

# Start with build
docker-compose up --build -d

# Start specific service
docker-compose up -d backend
```

### **Stop Services:**
```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Stop specific service
docker-compose stop backend
```

### **View Logs:**
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql
```

### **Service Status:**
```bash
# Check running services
docker-compose ps

# Check service health
docker-compose exec backend curl http://localhost:8080/api/health
```

## 🏗️ **Architecture**

```
┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Backend       │
│   React + Nginx │◄──►│   Spring Boot   │
│   Port: 3000    │    │   Port: 8080    │
└─────────────────┘    └─────────────────┘
                              │
                    ┌─────────┼─────────┐
                    │         │         │
            ┌───────▼───┐ ┌───▼───┐ ┌───▼────┐
            │   MySQL   │ │ Redis │ │ Kafka  │
            │ Port:3306 │ │ :6379 │ │ :9092  │
            └───────────┘ └───────┘ └────────┘
                              │
                    ┌─────────▼─────────┐
                    │   Elasticsearch   │
                    │    Port: 9200     │
                    └───────────────────┘
```

## 📁 **File Structure**

```
plan a/
├── Dockerfile                 # Backend Docker image
├── docker-compose.yml         # All services configuration
├── init.sql                   # Database initialization
├── .dockerignore             # Backend build context
├── RUN-DOCKER-COMPLETE.bat   # Start script
├── STOP-DOCKER.bat           # Stop script
├── DOCKER-LOGS.bat           # Logs viewer
└── ecommerce_frontend/
    ├── Dockerfile             # Frontend Docker image
    ├── nginx.conf             # Nginx configuration
    ├── .dockerignore          # Frontend build context
    └── .env.production        # Production environment
```

## 🔧 **Configuration**

### **Backend Environment:**
- **Profile**: `docker`
- **Database**: `mysql:3306/ecommerce_db`
- **Redis**: `redis:6379`
- **Elasticsearch**: `elasticsearch:9200`
- **Kafka**: `kafka:9092`

### **Frontend Environment:**
- **API Base**: Proxied through Nginx
- **WebSocket**: Proxied through Nginx
- **Static Assets**: Served by Nginx with caching

## 🐛 **Troubleshooting**

### **Services Won't Start:**
```bash
# Check Docker is running
docker --version

# Check available resources
docker system df

# Clean up
docker system prune -f
```

### **Database Connection Issues:**
```bash
# Check MySQL logs
docker-compose logs mysql

# Reset database
docker-compose down -v
docker-compose up -d mysql
```

### **Frontend Not Loading:**
```bash
# Check frontend logs
docker-compose logs frontend

# Rebuild frontend
docker-compose up --build frontend
```

### **Backend API Errors:**
```bash
# Check backend logs
docker-compose logs backend

# Check health
curl http://localhost:8080/api/health
```

## 📊 **Monitoring**

### **Health Checks:**
- All services have health checks configured
- Automatic restart on failure
- Startup dependencies managed

### **Logs:**
- Centralized logging with Docker Compose
- Structured JSON logging in backend
- Nginx access logs for frontend

### **Metrics:**
- Prometheus metrics: http://localhost:8080/actuator/prometheus
- Application metrics: http://localhost:8080/actuator/metrics
- Health status: http://localhost:8080/api/health

## 🚀 **Production Deployment**

### **Environment Variables:**
```bash
# Set production values
export MYSQL_ROOT_PASSWORD=secure_password
export JWT_SECRET=your_secure_jwt_secret
export SPRING_PROFILES_ACTIVE=prod
```

### **Scaling:**
```bash
# Scale backend instances
docker-compose up -d --scale backend=3

# Use load balancer
# Add nginx load balancer configuration
```

### **Security:**
- Change default passwords
- Use secrets management
- Enable HTTPS
- Configure firewall rules

**Your complete AI E-commerce platform is now fully containerized and ready to run with Docker!** 🐳🚀