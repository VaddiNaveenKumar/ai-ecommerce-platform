import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '@/contexts/AuthContext'
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  Chip,
  Button,
  CircularProgress,
  Alert
} from '@mui/material'
import { Receipt, Visibility } from '@mui/icons-material'
import { motion } from 'framer-motion'
import { orderService } from '@services/orderService'
import { format } from 'date-fns'

const MotionPaper = motion(Paper)

const Orders = () => {
  const { user } = useAuth()
  const [orders, setOrders] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    if (user?.userId) {
      fetchOrders()
    }
  }, [user])

  const fetchOrders = async () => {
    try {
      setLoading(true)
      const ordersData = await orderService.getUserOrders(user.userId)
      setOrders(ordersData.content || ordersData || [])
    } catch (error) {
      setError('Failed to load orders')
    } finally {
      setLoading(false)
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

  if (error) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Alert severity="error">{error}</Alert>
      </Container>
    )
  }

  if (orders.length === 0) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <MotionPaper
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          elevation={3}
          sx={{ p: 6, textAlign: 'center', borderRadius: 3 }}
        >
          <Receipt sx={{ fontSize: 80, color: 'text.secondary', mb: 2 }} />
          <Typography variant="h5" gutterBottom>
            No orders yet
          </Typography>
          <Typography variant="body1" color="text.secondary" paragraph>
            You haven't placed any orders yet. Start shopping to see your orders here.
          </Typography>
          <Button
            variant="contained"
            size="large"
            component={Link}
            to="/products"
            sx={{ mt: 2 }}
          >
            Start Shopping
          </Button>
        </MotionPaper>
      </Container>
    )
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" fontWeight="700" gutterBottom>
        My Orders
      </Typography>

      <Box display="flex" flexDirection="column" gap={3}>
        {orders.map((order, index) => (
          <MotionPaper
            key={order.id}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            elevation={2}
            sx={{ p: 3, borderRadius: 3 }}
          >
            <Grid container spacing={3} alignItems="center">
              <Grid item xs={12} md={3}>
                <Typography variant="h6" fontWeight="600">
                  Order #{order.orderNumber || `ORD-${order.id}`}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {order.createdAt ? format(new Date(order.createdAt), 'MMM dd, yyyy') : 'N/A'}
                </Typography>
              </Grid>

              <Grid item xs={12} md={2}>
                <Chip
                  label={order.status || 'PENDING'}
                  color={getStatusColor(order.status)}
                  variant="outlined"
                />
              </Grid>

              <Grid item xs={12} md={2}>
                <Typography variant="body2" color="text.secondary">
                  Total Amount
                </Typography>
                <Typography variant="h6" fontWeight="600" color="primary">
                  ${order.totalAmount?.toFixed(2) || '0.00'}
                </Typography>
              </Grid>

              <Grid item xs={12} md={3}>
                <Typography variant="body2" color="text.secondary">
                  Items
                </Typography>
                <Typography variant="body1">
                  {order.items?.length || 0} item(s)
                </Typography>
              </Grid>

              <Grid item xs={12} md={2}>
                <Button
                  variant="outlined"
                  startIcon={<Visibility />}
                  component={Link}
                  to={`/orders/${order.id}`}
                  fullWidth
                >
                  View Details
                </Button>
              </Grid>
            </Grid>

            {/* Order Items Preview */}
            {order.items && order.items.length > 0 && (
              <Box mt={2} pt={2} borderTop={1} borderColor="divider">
                <Typography variant="body2" color="text.secondary" gutterBottom>
                  Items in this order:
                </Typography>
                <Box display="flex" flexWrap="wrap" gap={1}>
                  {order.items.slice(0, 3).map((item, idx) => (
                    <Chip
                      key={idx}
                      label={`${item.productName || `Product ${item.productId}`} (${item.quantity})`}
                      size="small"
                      variant="outlined"
                    />
                  ))}
                  {order.items.length > 3 && (
                    <Chip
                      label={`+${order.items.length - 3} more`}
                      size="small"
                      variant="outlined"
                      color="primary"
                    />
                  )}
                </Box>
              </Box>
            )}
          </MotionPaper>
        ))}
      </Box>
    </Container>
  )
}

export default Orders