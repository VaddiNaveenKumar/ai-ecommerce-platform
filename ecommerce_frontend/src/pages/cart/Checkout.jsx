import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  TextField,
  Button,
  Stepper,
  Step,
  StepLabel,
  FormControl,
  FormLabel,
  RadioGroup,
  FormControlLabel,
  Radio,
  Divider,
  Alert
} from '@mui/material'
import { CreditCard, LocalShipping, CheckCircle } from '@mui/icons-material'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { motion } from 'framer-motion'
import { orderService } from '@services/orderService'
import { toast } from 'react-hot-toast'

const MotionPaper = motion(Paper)

const steps = ['Shipping Address', 'Payment Method', 'Review Order']

const shippingSchema = yup.object({
  street: yup.string().required('Street address is required'),
  city: yup.string().required('City is required'),
  state: yup.string().required('State is required'),
  zipCode: yup.string().required('ZIP code is required'),
  country: yup.string().required('Country is required')
})

const Checkout = () => {
  const navigate = useNavigate()
  const { items, totalAmount } = useSelector(state => state.cart)
  const [activeStep, setActiveStep] = useState(0)
  const [paymentMethod, setPaymentMethod] = useState('CREDIT_CARD')
  const [loading, setLoading] = useState(false)

  const {
    register,
    handleSubmit,
    formState: { errors },
    getValues
  } = useForm({
    resolver: yupResolver(shippingSchema),
    defaultValues: {
      street: '',
      city: '',
      state: '',
      zipCode: '',
      country: 'USA'
    }
  })

  useEffect(() => {
    if (!items || items.length === 0) {
      navigate('/cart')
    }
  }, [items, navigate])

  const handleNext = () => {
    if (activeStep === 0) {
      handleSubmit(onShippingSubmit)()
    } else {
      setActiveStep((prevStep) => prevStep + 1)
    }
  }

  const handleBack = () => {
    setActiveStep((prevStep) => prevStep - 1)
  }

  const onShippingSubmit = (data) => {
    setActiveStep(1)
  }

  const handlePlaceOrder = async () => {
    try {
      setLoading(true)
      const shippingData = getValues()
      
      const orderData = {
        items: items.map(item => ({
          productId: item.productId,
          quantity: item.quantity
        })),
        shippingAddress: shippingData,
        paymentMethod
      }

      const order = await orderService.createOrder(orderData)
      toast.success('Order placed successfully!')
      navigate(`/orders/${order.id}`)
    } catch (error) {
      toast.error('Failed to place order')
    } finally {
      setLoading(false)
    }
  }

  const renderStepContent = (step) => {
    switch (step) {
      case 0:
        return (
          <Box>
            <Typography variant="h6" gutterBottom>
              Shipping Address
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  {...register('street')}
                  label="Street Address"
                  fullWidth
                  error={!!errors.street}
                  helperText={errors.street?.message}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  {...register('city')}
                  label="City"
                  fullWidth
                  error={!!errors.city}
                  helperText={errors.city?.message}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  {...register('state')}
                  label="State"
                  fullWidth
                  error={!!errors.state}
                  helperText={errors.state?.message}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  {...register('zipCode')}
                  label="ZIP Code"
                  fullWidth
                  error={!!errors.zipCode}
                  helperText={errors.zipCode?.message}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  {...register('country')}
                  label="Country"
                  fullWidth
                  error={!!errors.country}
                  helperText={errors.country?.message}
                />
              </Grid>
            </Grid>
          </Box>
        )
      case 1:
        return (
          <Box>
            <Typography variant="h6" gutterBottom>
              Payment Method
            </Typography>
            <FormControl component="fieldset">
              <FormLabel component="legend">Select Payment Method</FormLabel>
              <RadioGroup
                value={paymentMethod}
                onChange={(e) => setPaymentMethod(e.target.value)}
              >
                <FormControlLabel
                  value="CREDIT_CARD"
                  control={<Radio />}
                  label={
                    <Box display="flex" alignItems="center" gap={1}>
                      <CreditCard />
                      Credit Card
                    </Box>
                  }
                />
                <FormControlLabel
                  value="PAYPAL"
                  control={<Radio />}
                  label="PayPal"
                />
                <FormControlLabel
                  value="BANK_TRANSFER"
                  control={<Radio />}
                  label="Bank Transfer"
                />
              </RadioGroup>
            </FormControl>
          </Box>
        )
      case 2:
        return (
          <Box>
            <Typography variant="h6" gutterBottom>
              Review Your Order
            </Typography>
            <Paper elevation={1} sx={{ p: 2, mb: 3 }}>
              <Typography variant="subtitle1" fontWeight="600" gutterBottom>
                Order Items
              </Typography>
              {items?.map((item) => (
                <Box key={item.id} display="flex" justifyContent="space-between" mb={1}>
                  <Typography variant="body2">
                    {item.productName} x {item.quantity}
                  </Typography>
                  <Typography variant="body2" fontWeight="500">
                    ${(item.price * item.quantity).toFixed(2)}
                  </Typography>
                </Box>
              ))}
              <Divider sx={{ my: 1 }} />
              <Box display="flex" justifyContent="space-between">
                <Typography variant="h6">Total:</Typography>
                <Typography variant="h6" color="primary">
                  ${((totalAmount || 0) * 1.08).toFixed(2)}
                </Typography>
              </Box>
            </Paper>

            <Paper elevation={1} sx={{ p: 2, mb: 3 }}>
              <Typography variant="subtitle1" fontWeight="600" gutterBottom>
                Shipping Address
              </Typography>
              <Typography variant="body2">
                {getValues('street')}<br />
                {getValues('city')}, {getValues('state')} {getValues('zipCode')}<br />
                {getValues('country')}
              </Typography>
            </Paper>

            <Paper elevation={1} sx={{ p: 2 }}>
              <Typography variant="subtitle1" fontWeight="600" gutterBottom>
                Payment Method
              </Typography>
              <Typography variant="body2">
                {paymentMethod.replace('_', ' ')}
              </Typography>
            </Paper>
          </Box>
        )
      default:
        return null
    }
  }

  if (!items || items.length === 0) {
    return null
  }

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Typography variant="h4" fontWeight="700" gutterBottom>
        Checkout
      </Typography>

      <Stepper activeStep={activeStep} sx={{ mb: 4 }}>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>

      <MotionPaper
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        elevation={3}
        sx={{ p: 4, borderRadius: 3 }}
      >
        {renderStepContent(activeStep)}

        <Box display="flex" justifyContent="space-between" mt={4}>
          <Button
            disabled={activeStep === 0}
            onClick={handleBack}
            variant="outlined"
          >
            Back
          </Button>
          
          {activeStep === steps.length - 1 ? (
            <Button
              variant="contained"
              onClick={handlePlaceOrder}
              disabled={loading}
              size="large"
              sx={{ px: 4 }}
            >
              {loading ? 'Placing Order...' : 'Place Order'}
            </Button>
          ) : (
            <Button
              variant="contained"
              onClick={handleNext}
              size="large"
              sx={{ px: 4 }}
            >
              Next
            </Button>
          )}
        </Box>
      </MotionPaper>

      {/* Order Summary Sidebar */}
      <Paper elevation={2} sx={{ p: 3, mt: 4, borderRadius: 3 }}>
        <Typography variant="h6" fontWeight="600" gutterBottom>
          Order Summary
        </Typography>
        <Box display="flex" justifyContent="space-between" mb={1}>
          <Typography>Subtotal:</Typography>
          <Typography>${totalAmount?.toFixed(2) || '0.00'}</Typography>
        </Box>
        <Box display="flex" justifyContent="space-between" mb={1}>
          <Typography>Tax:</Typography>
          <Typography>${((totalAmount || 0) * 0.08).toFixed(2)}</Typography>
        </Box>
        <Box display="flex" justifyContent="space-between" mb={1}>
          <Typography>Shipping:</Typography>
          <Typography>Free</Typography>
        </Box>
        <Divider sx={{ my: 1 }} />
        <Box display="flex" justifyContent="space-between">
          <Typography variant="h6" fontWeight="600">Total:</Typography>
          <Typography variant="h6" fontWeight="600" color="primary">
            ${((totalAmount || 0) * 1.08).toFixed(2)}
          </Typography>
        </Box>
      </Paper>
    </Container>
  )
}

export default Checkout