import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  Button,
  IconButton,
  TextField,
  Divider,
  Card,
  CardContent,
  CardMedia,
  Alert
} from '@mui/material'
import { Delete, Add, Remove, ShoppingCartOutlined } from '@mui/icons-material'
import { motion } from 'framer-motion'
import { fetchCart, updateCartItem, removeCartItem } from '@store/slices/cartSlice'
import { toast } from 'react-hot-toast'

const MotionPaper = motion(Paper)
const MotionCard = motion(Card)

const Cart = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { items, totalAmount, itemCount, loading } = useSelector(state => state.cart)

  useEffect(() => {
    dispatch(fetchCart())
  }, [dispatch])

  const handleUpdateQuantity = async (itemId, newQuantity) => {
    if (newQuantity <= 0) {
      handleRemoveItem(itemId)
      return
    }

    try {
      await dispatch(updateCartItem({ itemId, quantity: newQuantity })).unwrap()
    } catch (error) {
      toast.error('Failed to update quantity')
    }
  }

  const handleRemoveItem = async (itemId) => {
    try {
      await dispatch(removeCartItem(itemId)).unwrap()
      toast.success('Item removed from cart')
    } catch (error) {
      toast.error('Failed to remove item')
    }
  }

  const handleCheckout = () => {
    navigate('/checkout')
  }

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Box display="flex" justifyContent="center">
          <div>Loading cart...</div>
        </Box>
      </Container>
    )
  }

  if (!items || items.length === 0) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <MotionPaper
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          elevation={3}
          sx={{ p: 6, textAlign: 'center', borderRadius: 3 }}
        >
          <ShoppingCartOutlined sx={{ fontSize: 80, color: 'text.secondary', mb: 2 }} />
          <Typography variant="h5" gutterBottom>
            Your cart is empty
          </Typography>
          <Typography variant="body1" color="text.secondary" paragraph>
            Looks like you haven't added any items to your cart yet.
          </Typography>
          <Button
            variant="contained"
            size="large"
            onClick={() => navigate('/products')}
            sx={{ mt: 2 }}
          >
            Continue Shopping
          </Button>
        </MotionPaper>
      </Container>
    )
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" fontWeight="700" gutterBottom>
        Shopping Cart ({itemCount} items)
      </Typography>

      <Grid container spacing={4}>
        {/* Cart Items */}
        <Grid item xs={12} md={8}>
          <Box display="flex" flexDirection="column" gap={2}>
            {items.map((item, index) => (
              <MotionCard
                key={item.id}
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: index * 0.1 }}
                elevation={2}
                sx={{ borderRadius: 2 }}
              >
                <CardContent>
                  <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={3}>
                      <CardMedia
                        component="img"
                        height="120"
                        image={item.productImage || `https://via.placeholder.com/150x120?text=${item.productName}`}
                        alt={item.productName}
                        sx={{ borderRadius: 1, objectFit: 'cover' }}
                      />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                      <Typography variant="h6" fontWeight="600" gutterBottom>
                        {item.productName}
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        Price: ${item.price}
                      </Typography>
                    </Grid>
                    <Grid item xs={12} sm={3}>
                      <Box display="flex" alignItems="center" gap={1}>
                        <IconButton
                          size="small"
                          onClick={() => handleUpdateQuantity(item.id, item.quantity - 1)}
                        >
                          <Remove />
                        </IconButton>
                        <TextField
                          value={item.quantity}
                          onChange={(e) => {
                            const value = parseInt(e.target.value) || 1
                            handleUpdateQuantity(item.id, value)
                          }}
                          size="small"
                          sx={{ width: 60 }}
                          inputProps={{ min: 1, type: 'number', style: { textAlign: 'center' } }}
                        />
                        <IconButton
                          size="small"
                          onClick={() => handleUpdateQuantity(item.id, item.quantity + 1)}
                        >
                          <Add />
                        </IconButton>
                      </Box>
                    </Grid>
                    <Grid item xs={12} sm={2}>
                      <Box display="flex" flexDirection="column" alignItems="flex-end">
                        <Typography variant="h6" fontWeight="600" color="primary">
                          ${item.subtotal?.toFixed(2) || (item.price * item.quantity).toFixed(2)}
                        </Typography>
                        <IconButton
                          color="error"
                          onClick={() => handleRemoveItem(item.id)}
                          sx={{ mt: 1 }}
                        >
                          <Delete />
                        </IconButton>
                      </Box>
                    </Grid>
                  </Grid>
                </CardContent>
              </MotionCard>
            ))}
          </Box>
        </Grid>

        {/* Order Summary */}
        <Grid item xs={12} md={4}>
          <MotionPaper
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            elevation={3}
            sx={{ p: 3, borderRadius: 3, position: 'sticky', top: 100 }}
          >
            <Typography variant="h6" fontWeight="600" gutterBottom>
              Order Summary
            </Typography>
            
            <Box display="flex" justifyContent="space-between" mb={1}>
              <Typography variant="body1">Subtotal:</Typography>
              <Typography variant="body1">${totalAmount?.toFixed(2) || '0.00'}</Typography>
            </Box>
            
            <Box display="flex" justifyContent="space-between" mb={1}>
              <Typography variant="body1">Shipping:</Typography>
              <Typography variant="body1">Free</Typography>
            </Box>
            
            <Box display="flex" justifyContent="space-between" mb={1}>
              <Typography variant="body1">Tax:</Typography>
              <Typography variant="body1">${((totalAmount || 0) * 0.08).toFixed(2)}</Typography>
            </Box>
            
            <Divider sx={{ my: 2 }} />
            
            <Box display="flex" justifyContent="space-between" mb={3}>
              <Typography variant="h6" fontWeight="600">Total:</Typography>
              <Typography variant="h6" fontWeight="600" color="primary">
                ${((totalAmount || 0) * 1.08).toFixed(2)}
              </Typography>
            </Box>

            <Button
              variant="contained"
              size="large"
              fullWidth
              onClick={handleCheckout}
              sx={{ py: 1.5, fontSize: '1.1rem', fontWeight: 600 }}
            >
              Proceed to Checkout
            </Button>

            <Button
              variant="outlined"
              size="large"
              fullWidth
              onClick={() => navigate('/products')}
              sx={{ mt: 2, py: 1.5 }}
            >
              Continue Shopping
            </Button>

            <Alert severity="info" sx={{ mt: 3 }}>
              Free shipping on orders over $50!
            </Alert>
          </MotionPaper>
        </Grid>
      </Grid>
    </Container>
  )
}

export default Cart