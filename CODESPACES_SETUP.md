# 🚀 GitHub Codespaces Setup Guide

## Step 1: Upload to GitHub

1. **Create a new repository on GitHub**:
   - Go to https://github.com/new
   - Repository name: `ai-ecommerce-platform`
   - Make it Public (for free Codespaces)
   - Don't initialize with README (we already have one)

2. **Push your code**:
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/ai-ecommerce-platform.git
   git branch -M main
   git push -u origin main
   ```

## Step 2: Create Codespace

1. **Go to your GitHub repository**
2. **Click the green "Code" button**
3. **Select "Codespaces" tab**
4. **Click "Create codespace on main"**

## Step 3: Start the Platform

Once your Codespace loads:

1. **Run the startup script**:
   ```bash
   chmod +x start.sh
   ./start.sh
   ```

2. **Wait for services to start** (about 2-3 minutes)

## Step 4: Access Your Application

After startup completes, you'll see URLs like:
- **Frontend**: `https://YOUR_CODESPACE-3000.app.github.dev`
- **Backend API**: `https://YOUR_CODESPACE-8080.app.github.dev`
- **Swagger UI**: `https://YOUR_CODESPACE-8080.app.github.dev/swagger-ui.html`

## 🔧 Available Commands

```bash
# Start all services
./start.sh

# Check service status
docker-compose ps

# View logs
docker-compose logs backend
docker-compose logs frontend

# Stop all services
docker-compose down
```

## 📊 Service Ports

| Service | Port | URL |
|---------|------|-----|
| Frontend | 3000 | `https://YOUR_CODESPACE-3000.app.github.dev` |
| Backend | 8080 | `https://YOUR_CODESPACE-8080.app.github.dev` |
| MySQL | 3307 | Internal only |
| Redis | 6379 | Internal only |
| Elasticsearch | 9200 | Internal only |
| Kafka | 9092 | Internal only |

## 🎯 Test the Platform

1. **Open Frontend URL** in your browser
2. **Register a new account**
3. **Login and explore the dashboard**
4. **Check API documentation** at Swagger UI

## 💡 Tips

- **Codespaces auto-sleep** after 30 minutes of inactivity
- **Free tier**: 60 hours/month for personal accounts
- **Port forwarding** is automatic for exposed ports
- **VS Code extensions** are pre-installed

## 🔍 Troubleshooting

**If services don't start:**
```bash
docker-compose down
docker-compose up -d
```

**Check service health:**
```bash
docker-compose ps
docker logs ecommerce-backend
```

**Restart specific service:**
```bash
docker-compose restart backend
```

## 🌟 Features Available

- ✅ User Authentication (JWT + OAuth2)
- ✅ Product Catalog Management
- ✅ Shopping Cart & Orders
- ✅ Payment Processing
- ✅ AI Recommendations
- ✅ Real-time Search
- ✅ Admin Dashboard
- ✅ API Documentation

## 📚 Next Steps

1. **Explore the API** using Swagger UI
2. **Test different features** in the frontend
3. **Check the admin dashboard**
4. **Review the code structure**
5. **Customize for your needs**

---

**Happy Coding! 🎉**