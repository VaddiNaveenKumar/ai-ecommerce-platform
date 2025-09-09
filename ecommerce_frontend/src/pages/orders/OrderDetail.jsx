import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  Chip,
  Divider,
  CircularProgress,
  Alert,
  Stepper,
  Step,
  StepLabel
} from '@mui/material'
import { motion } from 'framer-motion'
import { orderService } from '@services/orderService'
import { format } from 'date-fns'

const MotionPaper = motion(Paper)

const orderSteps = ['Order Placed', 'Confirmed', 'Shipped', 'Delivered']

const OrderDetail = () => {
  const { id } = useParams()
  const [order, setOrder] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    fetchOrder()
  }, [id])

  const fetchOrder = async () => {
    try {
      setLoading(true)
      const orderData = await orderService.getOrder(id)
      setOrder(orderData)
    } catch (error) {
      setError('Failed to load order details')
    } finally {
      setLoading(false)
    }
  }

  const getActiveStep = (status) => {
    switch (status?.toUpperCase()) {
      case 'PENDING':
        return 0
      case 'CONFIRMED':
        return 1
      case 'SHIPPED':
        return 2
      case 'DELIVERED':
        return 3
      default:
        return 0
    }
  }

  const getStatusColor = (status) => {
    switch (status?.toUpperCase()) {
      case 'PENDING':
        return 'warning'
      case 'CONFIRMED':
        return 'info'
      case 'SHIPPED':
        return 'primary'
      case 'DELIVERED':
        return 'success'
      case 'CANCELLED':
        return 'error'
      default:
        return 'default'
    }
  }

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Box display="flex" justifyContent="center">
          <CircularProgress size={60} />
        </Box>
      </Container>
    )
  }

  if (error || !order) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Alert severity="error">{error || 'Order not found'}</Alert>
      </Container>
    )
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" fontWeight="700">
          Order #{order.orderNumber || `ORD-${order.id}`}
        </Typography>
        <Chip
          label={order.status || 'PENDING'}
          color={getStatusColor(order.status)}
          size="large"
        />
      </Box>

      {/* Order Status Stepper */}
      <MotionPaper
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        elevation={2}
        sx={{ p: 4, mb: 4, borderRadius: 3 }}
      >
        <Typography variant="h6" fontWeight="600" gutterBottom>
          Order Status
        </Typography>
        <Stepper activeStep={getActiveStep(order.status)} alternativeLabel>
          {orderSteps.map((label) => (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
            </Step>
          ))}
        </Stepper>
      </MotionPaper>

      <Grid container spacing={4}>
        {/* Order Items */}
        <Grid item xs={12} md={8}>
          <MotionPaper
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            elevation={2}
            sx={{ p: 3, borderRadius: 3 }}
          >
            <Typography variant="h6" fontWeight="600" gutterBottom>
              Order Items
            </Typography>
            
            {order.items?.map((item, index) => (
              <Box key={index}>
                <Grid container spacing={2} alignItems="center" py={2}>
                  <Grid item xs={12} sm={3}>
                    <img
                      src={item.productImage || `https://via.placeholder.com/100x80?text=${item.productName}`}
                      alt={item.productName}
                      style={{
                        width: '100%',
                        maxWidth: '100px',
                        height: '80px',
                        objectFit: 'cover',
                        borderRadius: '8px'
                      }}
                    />
                  </Grid>
                  <Grid item xs={12} sm={5}>
                    <Typography variant="h6" fontWeight="600">
                      {item.productName || `Product ${item.productId}`}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Quantity: {item.quantity}
                    </Typography>
                  </Grid>
                  <Grid item xs={12} sm={2}>
                    <Typography variant="body1">
                      ${item.unitPrice?.toFixed(2) || '0.00'}
                    </Typography>
                  </Grid>
                  <Grid item xs={12} sm={2}>
                    <Typography variant="h6" fontWeight="600" color="primary">
                      ${((item.unitPrice || 0) * item.quantity).toFixed(2)}
                    </Typography>
                  </Grid>
                </Grid>
                {index < (order.items?.length || 0) - 1 && <Divider />}
              </Box>
            ))}
          </MotionPaper>
        </Grid>

        {/* Order Summary */}
        <Grid item xs={12} md={4}>
          <MotionPaper
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            elevation={2}
            sx={{ p: 3, borderRadius: 3, mb: 3 }}
          >
            <Typography variant="h6" fontWeight="600" gutterBottom>
              Order Summary
            </Typography>
            
            <Box display="flex" justifyContent="space-between" mb={1}>
              <Typography>Subtotal:</Typography>
              <Typography>${order.subtotal?.toFixed(2) || order.totalAmount?.toFixed(2) || '0.00'}</Typography>
            </Box>
            
            <Box display="flex" justifyContent="space-between" mb={1}>
              <Typography>Tax:</Typography>
              <Typography>${(order.tax || (order.totalAmount || 0) * 0.08).toFixed(2)}</Typography>
            </Box>
            
            <Box display="flex" justifyContent="space-between" mb={1}>
              <Typography>Shipping:</Typography>
              <Typography>Free</Typography>
            </Box>
            
            <Divider sx={{ my: 2 }} />
            
            <Box display="flex" justifyContent="space-between">
              <Typography variant="h6" fontWeight="600">Total:</Typography>
              <Typography variant="h6" fontWeight="600" color="primary">
                ${order.totalAmount?.toFixed(2) || '0.00'}
              </Typography>
            </Box>
          </MotionPaper>

          {/* Shipping Address */}
          <MotionPaper
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ delay: 0.1 }}
            elevation={2}
            sx={{ p: 3, borderRadius: 3, mb: 3 }}
          >
            <Typography variant="h6" fontWeight="600" gutterBottom>
              Shipping Address
            </Typography>
            <Typography variant="body2">
              {order.shippingAddress ? (
                <>
                  {order.shippingAddress.street}<br />
                  {order.shippingAddress.city}, {order.shippingAddress.state} {order.shippingAddress.zipCode}<br />
                  {order.shippingAddress.country}
                </>
              ) : (
                'Address not available'
              )}
            </Typography>
          </MotionPaper>

          {/* Order Info */}
          <MotionPaper
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ delay: 0.2 }}
            elevation={2}
            sx={{ p: 3, borderRadius: 3 }}
          >
            <Typography variant="h6" fontWeight="600" gutterBottom>
              Order Information
            </Typography>
            <Box mb={1}>
              <Typography variant="body2" color="text.secondary">
                Order Date:
              </Typography>
              <Typography variant="body1">
                {order.createdAt ? format(new Date(order.createdAt), 'MMM dd, yyyy HH:mm') : 'N/A'}
              </Typography>
            </Box>
            <Box mb={1}>
              <Typography variant="body2" color="text.secondary">
                Payment Method:
              </Typography>
              <Typography variant="body1">
                {order.paymentMethod?.replace('_', ' ') || 'Credit Card'}
              </Typography>
            </Box>
            <Box>
              <Typography variant="body2" color="text.secondary">
                Order Status:
              </Typography>
              <Typography variant="body1">
                {order.status || 'PENDING'}
              </Typography>
            </Box>
          </MotionPaper>
        </Grid>
      </Grid>
    </Container>
  )
}

export default OrderDetail