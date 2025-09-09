# 🚀 Complete Frontend Development Guide

## 📋 Table of Contents
1. [API Documentation](#api-documentation)
2. [React + Vite Setup](#react--vite-setup)
3. [Frontend Architecture](#frontend-architecture)
4. [API Integration](#api-integration)
5. [Authentication System](#authentication-system)
6. [Component Examples](#component-examples)
7. [State Management](#state-management)
8. [Styling & UI](#styling--ui)
9. [Testing](#testing)
10. [Deployment](#deployment)

---

# API Documentation

## 🌐 Base URL
```
http://localhost:8080
```

## 🔐 Authentication
JWT Bearer Token required for protected endpoints:
```javascript
headers: {
  'Authorization': `Bearer ${token}`,
  'Content-Type': 'application/json'
}
```

## 📚 Complete API Endpoints

### 🔑 Authentication APIs

#### Register User
```javascript
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "Password123!",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890"
}

// Response
{
  "message": "User registered successfully",
  "userId": 1
}
```

#### Login User
```javascript
POST /api/auth/login
{
  "username": "john_doe",
  "password": "Password123!"
}

// Response
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "john_doe",
  "role": "CUSTOMER"
}
```

### 🛍️ Product APIs

#### Get All Products
```javascript
GET /api/products?page=0&size=10

// Response
{
  "content": [
    {
      "id": 1,
      "name": "iPhone 15 Pro",
      "description": "Latest iPhone model",
      "basePrice": 999.99,
      "brand": "Apple",
      "category": "Electronics",
      "stockQuantity": 50,
      "rating": 4.8,
      "featured": true,
      "imageUrl": "/api/files/products/1/image.jpg"
    }
  ],
  "totalElements": 100,
  "totalPages": 10,
  "size": 10,
  "number": 0
}
```

#### Get Product by ID
```javascript
GET /api/products/1

// Response: Single product object
```

#### Search Products
```javascript
GET /api/search?q=laptop&minPrice=500&maxPrice=2000&page=0&size=10

// Advanced search with Elasticsearch
```

#### Get Product Recommendations
```javascript
GET /api/ai/recommendations/1?limit=5

// AI-powered recommendations
```

### 🛒 Cart APIs

#### Get User Cart
```javascript
GET /api/cart
// Headers: Authorization: Bearer <token>

// Response
{
  "id": 1,
  "userId": 1,
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "iPhone 15 Pro",
      "quantity": 2,
      "price": 999.99,
      "subtotal": 1999.98
    }
  ],
  "totalAmount": 1999.98,
  "itemCount": 2
}
```

#### Add Item to Cart
```javascript
POST /api/cart/items
// Headers: Authorization: Bearer <token>
{
  "productId": 1,
  "quantity": 2
}
```

#### Update Cart Item
```javascript
PUT /api/cart/items/1
{
  "quantity": 3
}
```

#### Remove Cart Item
```javascript
DELETE /api/cart/items/1
```

### 📦 Order APIs

#### Create Order
```javascript
POST /api/orders
// Headers: Authorization: Bearer <token>
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ],
  "shippingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA"
  },
  "paymentMethod": "CREDIT_CARD"
}

// Response
{
  "id": 1,
  "orderNumber": "ORD-001",
  "status": "PENDING",
  "totalAmount": 1999.98,
  "createdAt": "2024-01-01T10:00:00Z"
}
```

#### Get User Orders
```javascript
GET /api/orders/user/1?page=0&size=10
// Headers: Authorization: Bearer <token>
```

#### Get Order by ID
```javascript
GET /api/orders/1
// Headers: Authorization: Bearer <token>
```

### 🤖 AI APIs

#### Dynamic Pricing
```javascript
GET /api/ai/pricing/1

// Response
{
  "productId": 1,
  "basePrice": 999.99,
  "dynamicPrice": 949.99,
  "demandFactor": 0.95,
  "recommendation": "BUY_NOW"
}
```

#### Fraud Detection
```javascript
POST /api/ai/fraud/analyze
{
  "amount": 1500.00,
  "location": "international",
  "userId": 1
}

// Response
{
  "riskScore": 0.3,
  "riskLevel": "LOW",
  "recommendation": "APPROVE"
}
```

### 👤 User APIs

#### Get User Profile
```javascript
GET /api/users/profile
// Headers: Authorization: Bearer <token>
```

#### Update User Profile
```javascript
PUT /api/users/profile
// Headers: Authorization: Bearer <token>
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```

### 📁 File Upload APIs

#### Upload File
```javascript
POST /api/files/upload
// Content-Type: multipart/form-data
// FormData with 'file' field
```

### 🔔 Notification APIs

#### Get User Notifications
```javascript
GET /api/notifications
// Headers: Authorization: Bearer <token>
```

#### WebSocket Connection
```javascript
// Connect to: ws://localhost:8080/ws
// Subscribe to: /user/queue/notifications
```

---

# React + Vite Setup

## 🚀 Project Initialization

### 1. Create Vite Project
```bash
npm create vite@latest ecommerce-frontend -- --template react
cd ecommerce-frontend
npm install
```

### 2. Install Dependencies
```bash
# Core dependencies
npm install axios react-router-dom @reduxjs/toolkit react-redux

# UI Components
npm install @mui/material @emotion/react @emotion/styled
npm install @mui/icons-material @mui/x-data-grid

# Form handling
npm install react-hook-form @hookform/resolvers yup

# Utilities
npm install date-fns lodash
npm install react-hot-toast
npm install @tanstack/react-query

# WebSocket
npm install sockjs-client @stomp/stompjs

# Development
npm install -D @types/node
```

### 3. Project Structure
```
src/
├── components/          # Reusable components
│   ├── common/         # Common UI components
│   ├── forms/          # Form components
│   └── layout/         # Layout components
├── pages/              # Page components
│   ├── auth/           # Authentication pages
│   ├── products/       # Product pages
│   ├── cart/           # Cart page
│   └── orders/         # Order pages
├── services/           # API services
├── store/              # Redux store
├── hooks/              # Custom hooks
├── utils/              # Utility functions
├── constants/          # Constants
└── styles/             # Global styles
```

---

# Frontend Architecture

## 🏗️ Core Architecture

### 1. Vite Configuration
```javascript
// vite.config.js
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@components': path.resolve(__dirname, './src/components'),
      '@pages': path.resolve(__dirname, './src/pages'),
      '@services': path.resolve(__dirname, './src/services'),
      '@store': path.resolve(__dirname, './src/store'),
      '@utils': path.resolve(__dirname, './src/utils')
    }
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/ws': {
        target: 'ws://localhost:8080',
        ws: true
      }
    }
  }
})
```

### 2. Environment Configuration
```javascript
// .env.development
VITE_API_BASE_URL=http://localhost:8080
VITE_WS_URL=ws://localhost:8080/ws
VITE_APP_NAME=AI E-commerce Platform

// .env.production
VITE_API_BASE_URL=https://api.yourdomain.com
VITE_WS_URL=wss://api.yourdomain.com/ws
VITE_APP_NAME=AI E-commerce Platform
```

---

# API Integration

## 🔌 API Service Layer

### 1. Base API Configuration
```javascript
// src/services/api.js
import axios from 'axios'
import { toast } from 'react-hot-toast'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor - Add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// Response interceptor - Handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('authToken')
      window.location.href = '/login'
    }
    
    const message = error.response?.data?.message || 'An error occurred'
    toast.error(message)
    
    return Promise.reject(error)
  }
)

export default api
```

### 2. Authentication Service
```javascript
// src/services/authService.js
import api from './api'

export const authService = {
  // Register user
  register: async (userData) => {
    const response = await api.post('/api/auth/register', userData)
    return response.data
  },

  // Login user
  login: async (credentials) => {
    const response = await api.post('/api/auth/login', credentials)
    const { token, ...user } = response.data
    
    // Store token
    localStorage.setItem('authToken', token)
    localStorage.setItem('user', JSON.stringify(user))
    
    return response.data
  },

  // Logout user
  logout: () => {
    localStorage.removeItem('authToken')
    localStorage.removeItem('user')
  },

  // Get current user
  getCurrentUser: () => {
    const user = localStorage.getItem('user')
    return user ? JSON.parse(user) : null
  },

  // Check if user is authenticated
  isAuthenticated: () => {
    return !!localStorage.getItem('authToken')
  }
}
```

### 3. Product Service
```javascript
// src/services/productService.js
import api from './api'

export const productService = {
  // Get all products
  getProducts: async (params = {}) => {
    const response = await api.get('/api/products', { params })
    return response.data
  },

  // Get product by ID
  getProduct: async (id) => {
    const response = await api.get(`/api/products/${id}`)
    return response.data
  },

  // Search products
  searchProducts: async (query, filters = {}) => {
    const params = { q: query, ...filters }
    const response = await api.get('/api/search', { params })
    return response.data
  },

  // Get recommendations
  getRecommendations: async (userId, limit = 5) => {
    const response = await api.get(`/api/ai/recommendations/${userId}`, {
      params: { limit }
    })
    return response.data
  },

  // Get dynamic pricing
  getDynamicPricing: async (productId) => {
    const response = await api.get(`/api/ai/pricing/${productId}`)
    return response.data
  }
}
```

### 4. Cart Service
```javascript
// src/services/cartService.js
import api from './api'

export const cartService = {
  // Get user cart
  getCart: async () => {
    const response = await api.get('/api/cart')
    return response.data
  },

  // Add item to cart
  addItem: async (productId, quantity) => {
    const response = await api.post('/api/cart/items', {
      productId,
      quantity
    })
    return response.data
  },

  // Update cart item
  updateItem: async (itemId, quantity) => {
    const response = await api.put(`/api/cart/items/${itemId}`, {
      quantity
    })
    return response.data
  },

  // Remove cart item
  removeItem: async (itemId) => {
    await api.delete(`/api/cart/items/${itemId}`)
  },

  // Clear cart
  clearCart: async () => {
    await api.delete('/api/cart')
  }
}
```

### 5. Order Service
```javascript
// src/services/orderService.js
import api from './api'

export const orderService = {
  // Create order
  createOrder: async (orderData) => {
    const response = await api.post('/api/orders', orderData)
    return response.data
  },

  // Get user orders
  getUserOrders: async (userId, params = {}) => {
    const response = await api.get(`/api/orders/user/${userId}`, { params })
    return response.data
  },

  // Get order by ID
  getOrder: async (orderId) => {
    const response = await api.get(`/api/orders/${orderId}`)
    return response.data
  },

  // Cancel order
  cancelOrder: async (orderId) => {
    const response = await api.put(`/api/orders/${orderId}/cancel`)
    return response.data
  }
}
```

---

# Authentication System

## 🔐 Auth Implementation

### 1. Auth Context
```javascript
// src/contexts/AuthContext.jsx
import React, { createContext, useContext, useReducer, useEffect } from 'react'
import { authService } from '@services/authService'

const AuthContext = createContext()

const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN_SUCCESS':
      return {
        ...state,
        isAuthenticated: true,
        user: action.payload,
        loading: false
      }
    case 'LOGOUT':
      return {
        ...state,
        isAuthenticated: false,
        user: null,
        loading: false
      }
    case 'SET_LOADING':
      return {
        ...state,
        loading: action.payload
      }
    default:
      return state
  }
}

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, {
    isAuthenticated: false,
    user: null,
    loading: true
  })

  useEffect(() => {
    const user = authService.getCurrentUser()
    if (user && authService.isAuthenticated()) {
      dispatch({ type: 'LOGIN_SUCCESS', payload: user })
    } else {
      dispatch({ type: 'SET_LOADING', payload: false })
    }
  }, [])

  const login = async (credentials) => {
    dispatch({ type: 'SET_LOADING', payload: true })
    try {
      const userData = await authService.login(credentials)
      dispatch({ type: 'LOGIN_SUCCESS', payload: userData })
      return userData
    } catch (error) {
      dispatch({ type: 'SET_LOADING', payload: false })
      throw error
    }
  }

  const logout = () => {
    authService.logout()
    dispatch({ type: 'LOGOUT' })
  }

  const register = async (userData) => {
    dispatch({ type: 'SET_LOADING', payload: true })
    try {
      await authService.register(userData)
      dispatch({ type: 'SET_LOADING', payload: false })
    } catch (error) {
      dispatch({ type: 'SET_LOADING', payload: false })
      throw error
    }
  }

  return (
    <AuthContext.Provider value={{
      ...state,
      login,
      logout,
      register
    }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider')
  }
  return context
}
```

### 2. Protected Route Component
```javascript
// src/components/common/ProtectedRoute.jsx
import React from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '@/contexts/AuthContext'
import { CircularProgress, Box } from '@mui/material'

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, loading } = useAuth()
  const location = useLocation()

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="50vh">
        <CircularProgress />
      </Box>
    )
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />
  }

  return children
}

export default ProtectedRoute
```

### 3. Login Component
```javascript
// src/pages/auth/Login.jsx
import React, { useState } from 'react'
import { useNavigate, useLocation, Link } from 'react-router-dom'
import { useAuth } from '@/contexts/AuthContext'
import {
  Container,
  Paper,
  TextField,
  Button,
  Typography,
  Box,
  Alert
} from '@mui/material'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

const schema = yup.object({
  username: yup.string().required('Username is required'),
  password: yup.string().required('Password is required')
})

const Login = () => {
  const [error, setError] = useState('')
  const { login, loading } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  const from = location.state?.from?.pathname || '/'

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm({
    resolver: yupResolver(schema)
  })

  const onSubmit = async (data) => {
    try {
      setError('')
      await login(data)
      navigate(from, { replace: true })
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed')
    }
  }

  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 8, mb: 4 }}>
        <Paper elevation={3} sx={{ p: 4 }}>
          <Typography variant="h4" align="center" gutterBottom>
            Login
          </Typography>
          
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          <form onSubmit={handleSubmit(onSubmit)}>
            <TextField
              {...register('username')}
              label="Username"
              fullWidth
              margin="normal"
              error={!!errors.username}
              helperText={errors.username?.message}
            />
            
            <TextField
              {...register('password')}
              label="Password"
              type="password"
              fullWidth
              margin="normal"
              error={!!errors.password}
              helperText={errors.password?.message}
            />
            
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={loading}
            >
              {loading ? 'Logging in...' : 'Login'}
            </Button>
            
            <Box textAlign="center">
              <Link to="/register">
                Don't have an account? Register
              </Link>
            </Box>
          </form>
        </Paper>
      </Box>
    </Container>
  )
}

export default Login
```

---

# Component Examples

## 🧩 Key Components

### 1. Product List Component
```javascript
// src/components/products/ProductList.jsx
import React, { useState, useEffect } from 'react'
import {
  Grid,
  Card,
  CardMedia,
  CardContent,
  Typography,
  Button,
  Box,
  Pagination,
  CircularProgress
} from '@mui/material'
import { productService } from '@services/productService'
import { cartService } from '@services/cartService'
import { toast } from 'react-hot-toast'

const ProductList = ({ searchQuery, filters }) => {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(0)

  useEffect(() => {
    fetchProducts()
  }, [page, searchQuery, filters])

  const fetchProducts = async () => {
    try {
      setLoading(true)
      let data
      
      if (searchQuery) {
        data = await productService.searchProducts(searchQuery, {
          ...filters,
          page: page - 1,
          size: 12
        })
      } else {
        data = await productService.getProducts({
          page: page - 1,
          size: 12
        })
      }
      
      setProducts(data.content)
      setTotalPages(data.totalPages)
    } catch (error) {
      toast.error('Failed to load products')
    } finally {
      setLoading(false)
    }
  }

  const handleAddToCart = async (productId) => {
    try {
      await cartService.addItem(productId, 1)
      toast.success('Added to cart!')
    } catch (error) {
      toast.error('Failed to add to cart')
    }
  }

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" p={4}>
        <CircularProgress />
      </Box>
    )
  }

  return (
    <Box>
      <Grid container spacing={3}>
        {products.map((product) => (
          <Grid item xs={12} sm={6} md={4} lg={3} key={product.id}>
            <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
              <CardMedia
                component="img"
                height="200"
                image={product.imageUrl || '/placeholder.jpg'}
                alt={product.name}
              />
              <CardContent sx={{ flexGrow: 1 }}>
                <Typography gutterBottom variant="h6" component="h2">
                  {product.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {product.description}
                </Typography>
                <Typography variant="h6" color="primary" sx={{ mt: 2 }}>
                  ${product.basePrice}
                </Typography>
              </CardContent>
              <Box sx={{ p: 2 }}>
                <Button
                  fullWidth
                  variant="contained"
                  onClick={() => handleAddToCart(product.id)}
                  disabled={product.stockQuantity === 0}
                >
                  {product.stockQuantity === 0 ? 'Out of Stock' : 'Add to Cart'}
                </Button>
              </Box>
            </Card>
          </Grid>
        ))}
      </Grid>
      
      {totalPages > 1 && (
        <Box display="flex" justifyContent="center" mt={4}>
          <Pagination
            count={totalPages}
            page={page}
            onChange={(e, value) => setPage(value)}
            color="primary"
          />
        </Box>
      )}
    </Box>
  )
}

export default ProductList
```

### 2. Shopping Cart Component
```javascript
// src/components/cart/ShoppingCart.jsx
import React, { useState, useEffect } from 'react'
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Button,
  Divider,
  TextField
} from '@mui/material'
import { Delete, Add, Remove } from '@mui/icons-material'
import { cartService } from '@services/cartService'
import { toast } from 'react-hot-toast'

const ShoppingCart = ({ onCheckout }) => {
  const [cart, setCart] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchCart()
  }, [])

  const fetchCart = async () => {
    try {
      const data = await cartService.getCart()
      setCart(data)
    } catch (error) {
      toast.error('Failed to load cart')
    } finally {
      setLoading(false)
    }
  }

  const updateQuantity = async (itemId, newQuantity) => {
    if (newQuantity <= 0) {
      await removeItem(itemId)
      return
    }

    try {
      await cartService.updateItem(itemId, newQuantity)
      await fetchCart()
    } catch (error) {
      toast.error('Failed to update quantity')
    }
  }

  const removeItem = async (itemId) => {
    try {
      await cartService.removeItem(itemId)
      await fetchCart()
      toast.success('Item removed from cart')
    } catch (error) {
      toast.error('Failed to remove item')
    }
  }

  if (loading) return <div>Loading...</div>
  if (!cart || cart.items.length === 0) {
    return (
      <Box textAlign="center" py={4}>
        <Typography variant="h6">Your cart is empty</Typography>
      </Box>
    )
  }

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        Shopping Cart ({cart.itemCount} items)
      </Typography>
      
      <List>
        {cart.items.map((item) => (
          <React.Fragment key={item.id}>
            <ListItem>
              <ListItemText
                primary={item.productName}
                secondary={`$${item.price} each`}
              />
              <Box display="flex" alignItems="center" mr={2}>
                <IconButton
                  onClick={() => updateQuantity(item.id, item.quantity - 1)}
                >
                  <Remove />
                </IconButton>
                <TextField
                  value={item.quantity}
                  onChange={(e) => updateQuantity(item.id, parseInt(e.target.value))}
                  size="small"
                  sx={{ width: 60, mx: 1 }}
                  inputProps={{ min: 1, type: 'number' }}
                />
                <IconButton
                  onClick={() => updateQuantity(item.id, item.quantity + 1)}
                >
                  <Add />
                </IconButton>
              </Box>
              <ListItemSecondaryAction>
                <Box display="flex" alignItems="center">
                  <Typography variant="body1" sx={{ mr: 2 }}>
                    ${item.subtotal.toFixed(2)}
                  </Typography>
                  <IconButton onClick={() => removeItem(item.id)}>
                    <Delete />
                  </IconButton>
                </Box>
              </ListItemSecondaryAction>
            </ListItem>
            <Divider />
          </React.Fragment>
        ))}
      </List>
      
      <Box mt={3} p={2} bgcolor="grey.100">
        <Typography variant="h6" align="right">
          Total: ${cart.totalAmount.toFixed(2)}
        </Typography>
        <Button
          variant="contained"
          size="large"
          fullWidth
          sx={{ mt: 2 }}
          onClick={() => onCheckout(cart)}
        >
          Proceed to Checkout
        </Button>
      </Box>
    </Box>
  )
}

export default ShoppingCart
```

### 3. WebSocket Notifications
```javascript
// src/hooks/useWebSocket.js
import { useEffect, useRef } from 'react'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import { toast } from 'react-hot-toast'

export const useWebSocket = (userId) => {
  const clientRef = useRef(null)

  useEffect(() => {
    if (!userId) return

    const client = new Client({
      webSocketFactory: () => new SockJS(import.meta.env.VITE_WS_URL),
      debug: (str) => console.log('STOMP: ' + str),
      onConnect: () => {
        console.log('Connected to WebSocket')
        
        // Subscribe to user-specific notifications
        client.subscribe(`/user/queue/notifications`, (message) => {
          const notification = JSON.parse(message.body)
          toast.success(notification.message)
        })
        
        // Subscribe to broadcast notifications
        client.subscribe('/topic/notifications', (message) => {
          const notification = JSON.parse(message.body)
          toast.info(notification.message)
        })
      },
      onDisconnect: () => {
        console.log('Disconnected from WebSocket')
      }
    })

    client.activate()
    clientRef.current = client

    return () => {
      if (clientRef.current) {
        clientRef.current.deactivate()
      }
    }
  }, [userId])

  return clientRef.current
}
```

---

# State Management

## 🗄️ Redux Store Setup

### 1. Store Configuration
```javascript
// src/store/index.js
import { configureStore } from '@reduxjs/toolkit'
import authSlice from './slices/authSlice'
import cartSlice from './slices/cartSlice'
import productsSlice from './slices/productsSlice'

export const store = configureStore({
  reducer: {
    auth: authSlice,
    cart: cartSlice,
    products: productsSlice
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['persist/PERSIST']
      }
    })
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
```

### 2. Cart Slice
```javascript
// src/store/slices/cartSlice.js
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { cartService } from '@services/cartService'

// Async thunks
export const fetchCart = createAsyncThunk(
  'cart/fetchCart',
  async (_, { rejectWithValue }) => {
    try {
      return await cartService.getCart()
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

export const addToCart = createAsyncThunk(
  'cart/addToCart',
  async ({ productId, quantity }, { rejectWithValue }) => {
    try {
      await cartService.addItem(productId, quantity)
      return await cartService.getCart()
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    items: [],
    totalAmount: 0,
    itemCount: 0,
    loading: false,
    error: null
  },
  reducers: {
    clearCart: (state) => {
      state.items = []
      state.totalAmount = 0
      state.itemCount = 0
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCart.pending, (state) => {
        state.loading = true
      })
      .addCase(fetchCart.fulfilled, (state, action) => {
        state.loading = false
        state.items = action.payload.items
        state.totalAmount = action.payload.totalAmount
        state.itemCount = action.payload.itemCount
      })
      .addCase(fetchCart.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      .addCase(addToCart.fulfilled, (state, action) => {
        state.items = action.payload.items
        state.totalAmount = action.payload.totalAmount
        state.itemCount = action.payload.itemCount
      })
  }
})

export const { clearCart } = cartSlice.actions
export default cartSlice.reducer
```

---

# Styling & UI

## 🎨 Material-UI Theme

### 1. Custom Theme
```javascript
// src/theme/index.js
import { createTheme } from '@mui/material/styles'

export const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
      light: '#42a5f5',
      dark: '#1565c0'
    },
    secondary: {
      main: '#dc004e',
      light: '#ff5983',
      dark: '#9a0036'
    },
    background: {
      default: '#f5f5f5',
      paper: '#ffffff'
    }
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontSize: '2.5rem',
      fontWeight: 600
    },
    h2: {
      fontSize: '2rem',
      fontWeight: 600
    }
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: 8
        }
      }
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
        }
      }
    }
  }
})
```

### 2. Global Styles
```css
/* src/styles/global.css */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Roboto', sans-serif;
  background-color: #f5f5f5;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
}

.loading {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
}

.error {
  color: #d32f2f;
  text-align: center;
  padding: 16px;
}
```

---

# Testing

## 🧪 Testing Setup

### 1. Test Dependencies
```bash
npm install -D @testing-library/react @testing-library/jest-dom @testing-library/user-event vitest jsdom
```

### 2. Vitest Configuration
```javascript
// vitest.config.js
import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  test: {
    environment: 'jsdom',
    setupFiles: ['./src/test/setup.js']
  }
})
```

### 3. Test Setup
```javascript
// src/test/setup.js
import '@testing-library/jest-dom'
import { vi } from 'vitest'

// Mock localStorage
Object.defineProperty(window, 'localStorage', {
  value: {
    getItem: vi.fn(),
    setItem: vi.fn(),
    removeItem: vi.fn(),
    clear: vi.fn()
  }
})
```

### 4. Component Test Example
```javascript
// src/components/__tests__/ProductList.test.jsx
import { render, screen, waitFor } from '@testing-library/react'
import { vi } from 'vitest'
import ProductList from '../products/ProductList'
import { productService } from '@services/productService'

// Mock the service
vi.mock('@services/productService')

const mockProducts = {
  content: [
    {
      id: 1,
      name: 'Test Product',
      description: 'Test Description',
      basePrice: 99.99,
      stockQuantity: 10
    }
  ],
  totalPages: 1
}

describe('ProductList', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  test('renders products correctly', async () => {
    productService.getProducts.mockResolvedValue(mockProducts)

    render(<ProductList />)

    await waitFor(() => {
      expect(screen.getByText('Test Product')).toBeInTheDocument()
      expect(screen.getByText('$99.99')).toBeInTheDocument()
    })
  })

  test('shows loading state', () => {
    productService.getProducts.mockImplementation(() => new Promise(() => {}))

    render(<ProductList />)

    expect(screen.getByRole('progressbar')).toBeInTheDocument()
  })
})
```

---

# Deployment

## 🚀 Production Build

### 1. Build Configuration
```javascript
// package.json scripts
{
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview",
    "test": "vitest",
    "test:ui": "vitest --ui",
    "lint": "eslint src --ext .js,.jsx,.ts,.tsx",
    "lint:fix": "eslint src --ext .js,.jsx,.ts,.tsx --fix"
  }
}
```

### 2. Docker Configuration
```dockerfile
# Dockerfile
FROM node:18-alpine as build

WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production

COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### 3. Nginx Configuration
```nginx
# nginx.conf
events {
    worker_connections 1024;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    server {
        listen 80;
        server_name localhost;
        root /usr/share/nginx/html;
        index index.html;

        # Handle client-side routing
        location / {
            try_files $uri $uri/ /index.html;
        }

        # API proxy
        location /api {
            proxy_pass http://backend:8080;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
        }

        # WebSocket proxy
        location /ws {
            proxy_pass http://backend:8080;
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection "upgrade";
        }
    }
}
```

### 4. Environment Variables
```bash
# .env.production
VITE_API_BASE_URL=https://api.yourdomain.com
VITE_WS_URL=wss://api.yourdomain.com/ws
VITE_APP_NAME=AI E-commerce Platform
```

---

## 📱 Complete App Structure

```
src/
├── components/
│   ├── common/
│   │   ├── Header.jsx
│   │   ├── Footer.jsx
│   │   ├── Loading.jsx
│   │   └── ProtectedRoute.jsx
│   ├── products/
│   │   ├── ProductList.jsx
│   │   ├── ProductCard.jsx
│   │   ├── ProductDetail.jsx
│   │   └── ProductSearch.jsx
│   ├── cart/
│   │   ├── ShoppingCart.jsx
│   │   ├── CartItem.jsx
│   │   └── Checkout.jsx
│   └── orders/
│       ├── OrderList.jsx
│       ├── OrderDetail.jsx
│       └── OrderStatus.jsx
├── pages/
│   ├── auth/
│   │   ├── Login.jsx
│   │   └── Register.jsx
│   ├── Home.jsx
│   ├── Products.jsx
│   ├── Cart.jsx
│   ├── Orders.jsx
│   └── Profile.jsx
├── services/
│   ├── api.js
│   ├── authService.js
│   ├── productService.js
│   ├── cartService.js
│   └── orderService.js
├── store/
│   ├── index.js
│   └── slices/
│       ├── authSlice.js
│       ├── cartSlice.js
│       └── productsSlice.js
├── hooks/
│   ├── useAuth.js
│   ├── useCart.js
│   └── useWebSocket.js
├── contexts/
│   └── AuthContext.jsx
├── utils/
│   ├── constants.js
│   ├── helpers.js
│   └── validators.js
├── theme/
│   └── index.js
├── styles/
│   └── global.css
└── App.jsx
```

## 🎯 Quick Start Commands

```bash
# Create project
npm create vite@latest ecommerce-frontend -- --template react
cd ecommerce-frontend

# Install dependencies
npm install axios react-router-dom @reduxjs/toolkit react-redux
npm install @mui/material @emotion/react @emotion/styled
npm install react-hook-form @hookform/resolvers yup
npm install react-hot-toast @tanstack/react-query
npm install sockjs-client @stomp/stompjs

# Start development
npm run dev

# Build for production
npm run build

# Run tests
npm run test
```

**This guide provides everything needed to build a complete React frontend for the AI E-commerce Platform!** 🚀