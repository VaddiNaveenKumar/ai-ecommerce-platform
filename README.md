# AI-Powered E-commerce Platform

A highly scalable, secure, and extensible e-commerce backend built with Java 17+ and Spring Boot 3.x, featuring advanced AI capabilities for personalization, fraud detection, and dynamic pricing.

## 🚀 Features

### Core Functionality
- **User Management**: JWT/OAuth2 authentication, MFA, RBAC
- **Product Catalog**: Comprehensive product management with AI-generated metadata
- **Order Management**: Complete order lifecycle with AI-powered delivery predictions
- **Payment Processing**: Multi-gateway support with fraud detection
- **Search & Recommendations**: Elasticsearch-powered search with AI recommendations

### AI-Powered Capabilities
- **Personalized Recommendations**: ML-based product suggestions
- **Dynamic Pricing**: AI-driven pricing optimization
- **Fraud Detection**: Real-time payment and behavior analysis
- **Sentiment Analysis**: Review authenticity and sentiment scoring
- **Predictive Analytics**: Customer lifetime value and churn prediction

## 🏗️ Architecture

### Modular Design
```
├── core/           # Entities and repositories
├── service/        # Business logic and integrations
├── web/           # REST controllers and security
├── admin/         # Admin operations and analytics
└── ai-modules/    # AI/ML services and models
```

### Technology Stack
- **Backend**: Java 17, Spring Boot 3.x, Spring Security
- **Database**: MySQL/PostgreSQL with JPA/Hibernate
- **Caching**: Redis for sessions and cart persistence
- **Search**: Elasticsearch for product search and analytics
- **Messaging**: Apache Kafka for event-driven architecture
- **AI/ML**: TensorFlow integration for model inference
- **Documentation**: OpenAPI 3.0 with Swagger UI

## 🛠️ Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- MySQL 8.0+ or PostgreSQL 12+

### Quick Start with Docker
```bash
# Clone the repository
git clone <repository-url>
cd ai-ecommerce-platform

# Start infrastructure services
docker-compose up -d mysql redis elasticsearch kafka

# Build and run the application
mvn clean package
docker-compose up app
```

### Manual Setup
```bash
# Build all modules
mvn clean install

# Run the application
cd web
mvn spring-boot:run
```

### Environment Configuration
Create `.env` file or set environment variables:
```bash
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/ecommerce_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password

# JWT
JWT_SECRET=your-jwt-secret-key

# OAuth2
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

# Email
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-app-password
```

## 📚 API Documentation

Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

### Key Endpoints

#### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/oauth2/success` - OAuth2 callback

#### Products
- `GET /api/products` - List products with filters
- `GET /api/products/{id}` - Get product details
- `GET /api/products/search` - Search products
- `GET /api/products/{id}/recommendations` - Get recommendations

#### Orders
- `POST /api/orders` - Create order
- `GET /api/orders/{id}` - Get order details
- `GET /api/orders/user/{userId}` - Get user orders

#### Admin
- `GET /api/admin/dashboard` - Admin dashboard
- `GET /api/admin/analytics/sales` - Sales analytics
- `POST /api/admin/users/{id}/lock` - Lock user account

## 🧪 Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify
```

### Load Testing
Use the provided Postman collection for API testing and load testing scenarios.

## 🚀 Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t ecommerce-app .

# Run with docker-compose
docker-compose up -d
```

### CI/CD Pipeline
The project includes GitHub Actions workflow for:
- Automated testing
- Docker image building
- Deployment to staging/production

## 🔧 Configuration

### Application Profiles
- `dev` - Development environment
- `staging` - Staging environment  
- `prod` - Production environment

### Key Configuration Files
- `application.yml` - Main configuration
- `docker-compose.yml` - Infrastructure setup
- `Dockerfile` - Application containerization

## 🤖 AI Integration

### Model Integration
The platform supports:
- TensorFlow models for recommendations
- ONNX models for fraud detection
- REST API integration with external AI services

### AI Service Endpoints
- `/api/ai/recommendations/{userId}` - Get personalized recommendations
- `/api/ai/pricing/{productId}` - Get dynamic pricing
- `/api/ai/fraud/analyze` - Fraud risk assessment

## 📊 Monitoring & Observability

### Health Checks
- Application health: `/actuator/health`
- Metrics: `/actuator/metrics`
- Prometheus: `/actuator/prometheus`

### Logging
- Centralized logging with structured JSON format
- Log aggregation ready for ELK stack
- Distributed tracing support

## 🔒 Security Features

- JWT-based authentication with refresh tokens
- OAuth2 integration (Google Sign-In)
- Multi-factor authentication (MFA)
- Role-based access control (RBAC)
- API rate limiting
- SQL injection prevention
- XSS protection
- CSRF protection

## 🌟 Advanced Features

### Event-Driven Architecture
- Kafka integration for real-time events
- Order status updates
- Inventory management
- Notification triggers

### Caching Strategy
- Redis for user sessions
- Product catalog caching
- Search result caching
- AI model result caching

### Scalability Features
- Database read replicas support
- Horizontal scaling ready
- Microservices architecture
- Load balancer ready

## 📈 Performance Optimization

- Database indexing strategies
- Query optimization
- Connection pooling
- Async processing for heavy operations
- CDN integration for static assets

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the documentation
- Review the API documentation at `/swagger-ui.html`