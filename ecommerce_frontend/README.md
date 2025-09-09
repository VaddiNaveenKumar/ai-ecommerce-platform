# 🚀 AI E-commerce Frontend

A modern, responsive React frontend for the AI-powered e-commerce platform built with Vite, Material-UI, and Redux Toolkit.

## ✨ Features

- **Modern UI/UX**: Beautiful, responsive design with Material-UI components
- **Authentication**: JWT-based login/register with protected routes
- **Product Catalog**: Advanced search, filtering, and product details
- **Shopping Cart**: Real-time cart management with Redux
- **Checkout Process**: Multi-step checkout with form validation
- **Order Management**: Order history and detailed order tracking
- **User Profile**: Editable user profile with account statistics
- **Real-time Updates**: WebSocket integration for notifications
- **Performance**: Optimized with React Query and caching
- **Animations**: Smooth animations with Framer Motion

## 🛠️ Tech Stack

- **React 18** - Modern React with hooks
- **Vite** - Fast build tool and dev server
- **Material-UI v5** - Component library with custom theme
- **Redux Toolkit** - State management
- **React Router v6** - Client-side routing
- **React Hook Form** - Form handling with validation
- **Yup** - Schema validation
- **Axios** - HTTP client with interceptors
- **Framer Motion** - Animations and transitions
- **React Hot Toast** - Beautiful notifications

## 🚀 Quick Start

### Prerequisites
- Node.js 18+ 
- npm or yarn

### Installation

1. **Install dependencies:**
```bash
npm install
```

2. **Set up environment variables:**
```bash
# Copy environment file
cp .env.development .env.local

# Update API URL if needed
VITE_API_BASE_URL=http://localhost:8080
VITE_WS_URL=ws://localhost:8080/ws
```

3. **Start development server:**
```bash
npm run dev
```

4. **Open browser:**
Navigate to `http://localhost:3000`

## 📁 Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── common/         # Common components (ProtectedRoute, etc.)
│   ├── layout/         # Layout components (Header, Footer)
│   ├── products/       # Product-related components
│   ├── cart/           # Shopping cart components
│   ├── orders/         # Order components
│   └── auth/           # Authentication components
├── pages/              # Page components
│   ├── auth/           # Login, Register pages
│   ├── products/       # Product listing, detail pages
│   ├── cart/           # Cart, Checkout pages
│   ├── orders/         # Order history, detail pages
│   └── profile/        # User profile page
├── services/           # API service layer
│   ├── api.js          # Axios configuration
│   ├── authService.js  # Authentication APIs
│   ├── productService.js # Product APIs
│   ├── cartService.js  # Cart APIs
│   └── orderService.js # Order APIs
├── store/              # Redux store
│   ├── index.js        # Store configuration
│   └── slices/         # Redux slices
├── contexts/           # React contexts
├── hooks/              # Custom hooks
├── utils/              # Utility functions
└── theme/              # Material-UI theme
```

## 🎨 Design System

### Colors
- **Primary**: Blue (#2563eb)
- **Secondary**: Purple (#7c3aed)
- **Success**: Green (#10b981)
- **Warning**: Orange (#f59e0b)
- **Error**: Red (#ef4444)

### Typography
- **Font Family**: Inter
- **Headings**: 700 weight
- **Body**: 400 weight
- **Buttons**: 500 weight

### Components
- **Border Radius**: 12px (cards), 8px (buttons)
- **Shadows**: Subtle elevation system
- **Animations**: Smooth transitions with Framer Motion

## 🔧 Available Scripts

```bash
# Development
npm run dev          # Start dev server
npm run build        # Build for production
npm run preview      # Preview production build

# Testing
npm run test         # Run tests
npm run test:ui      # Run tests with UI

# Linting
npm run lint         # Run ESLint
npm run lint:fix     # Fix ESLint errors
```

## 🌐 API Integration

The frontend integrates with the backend API through:

- **Authentication**: JWT token management
- **Products**: Search, filtering, recommendations
- **Cart**: Real-time cart operations
- **Orders**: Order creation and tracking
- **User**: Profile management
- **AI Features**: Dynamic pricing, fraud detection

### API Configuration

```javascript
// Base URL configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

// Automatic token attachment
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})
```

## 🔐 Authentication Flow

1. **Login/Register**: User credentials sent to backend
2. **Token Storage**: JWT token stored in localStorage
3. **Auto-attach**: Token automatically attached to API requests
4. **Route Protection**: Protected routes check authentication status
5. **Auto-logout**: Automatic logout on token expiration

## 🛒 Key Features

### Product Catalog
- Advanced search with Elasticsearch
- Real-time filtering and sorting
- Product recommendations
- Dynamic pricing display

### Shopping Cart
- Real-time cart updates
- Quantity management
- Price calculations
- Persistent cart state

### Checkout Process
- Multi-step checkout flow
- Address management
- Payment method selection
- Order confirmation

### Order Management
- Order history with status tracking
- Detailed order views
- Order status updates
- Delivery tracking

## 📱 Responsive Design

- **Mobile First**: Optimized for mobile devices
- **Breakpoints**: xs, sm, md, lg, xl
- **Grid System**: Material-UI responsive grid
- **Touch Friendly**: Large touch targets

## 🚀 Performance Optimizations

- **Code Splitting**: Route-based code splitting
- **Lazy Loading**: Component lazy loading
- **Caching**: API response caching with React Query
- **Memoization**: React.memo for expensive components
- **Bundle Optimization**: Vite's optimized bundling

## 🧪 Testing

```bash
# Run all tests
npm run test

# Run tests in watch mode
npm run test:watch

# Run tests with coverage
npm run test:coverage
```

## 🚀 Deployment

### Build for Production
```bash
npm run build
```

### Docker Deployment
```bash
# Build Docker image
docker build -t ecommerce-frontend .

# Run container
docker run -p 3000:80 ecommerce-frontend
```

### Environment Variables
```bash
# Production environment
VITE_API_BASE_URL=https://api.yourdomain.com
VITE_WS_URL=wss://api.yourdomain.com/ws
VITE_APP_NAME=AI E-commerce Platform
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

---

**Built with ❤️ using React + Vite + Material-UI**