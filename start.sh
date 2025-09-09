#!/bin/bash
echo "🚀 Starting AI E-commerce Platform..."

# Wait for Docker to be ready
sleep 5

# Start all services
docker-compose up -d

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 30

# Show status
echo "📊 Service Status:"
docker-compose ps

echo ""
echo "🌐 Access URLs:"
echo "Frontend: https://$CODESPACE_NAME-3000.app.github.dev"
echo "Backend:  https://$CODESPACE_NAME-8080.app.github.dev"
echo "Swagger:  https://$CODESPACE_NAME-8080.app.github.dev/swagger-ui.html"
echo ""
echo "✅ Platform is ready!"