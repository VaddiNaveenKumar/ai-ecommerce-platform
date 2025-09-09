import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { Box } from '@mui/material'

// Layout Components
import Header from '@components/layout/Header'
import ProtectedRoute from '@components/common/ProtectedRoute'

// Pages
import Home from '@pages/Home'
import Login from '@pages/auth/Login'
import Register from '@pages/auth/Register'
import Products from '@pages/products/Products'
import ProductDetail from '@pages/products/ProductDetail'
import Cart from '@pages/cart/Cart'
import Checkout from '@pages/cart/Checkout'
import Orders from '@pages/orders/Orders'
import OrderDetail from '@pages/orders/OrderDetail'
import Profile from '@pages/profile/Profile'

function App() {
  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Header />
      
      <Box component="main" sx={{ flexGrow: 1 }}>
        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/products" element={<Products />} />
          <Route path="/products/:id" element={<ProductDetail />} />
          
          {/* Protected Routes */}
          <Route path="/cart" element={
            <ProtectedRoute>
              <Cart />
            </ProtectedRoute>
          } />
          <Route path="/checkout" element={
            <ProtectedRoute>
              <Checkout />
            </ProtectedRoute>
          } />
          <Route path="/orders" element={
            <ProtectedRoute>
              <Orders />
            </ProtectedRoute>
          } />
          <Route path="/orders/:id" element={
            <ProtectedRoute>
              <OrderDetail />
            </ProtectedRoute>
          } />
          <Route path="/profile" element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          } />
        </Routes>
      </Box>
    </Box>
  )
}

export default App