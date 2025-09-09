import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import {
  Container,
  Grid,
  Typography,
  Button,
  Box,
  Paper,
  Chip,
  Rating,
  Divider,
  TextField,
  CircularProgress,
  Alert
} from '@mui/material'
import { ShoppingCart, Favorite, Share } from '@mui/icons-material'
import { motion } from 'framer-motion'
import { productService } from '@services/productService'
import { addToCart } from '@store/slices/cartSlice'
import { toast } from 'react-hot-toast'

const MotionBox = motion(Box)

const ProductDetail = () => {
  const { id } = useParams()
  const dispatch = useDispatch()
  const [product, setProduct] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [quantity, setQuantity] = useState(1)
  const [recommendations, setRecommendations] = useState([])

  useEffect(() => {
    fetchProduct()
  }, [id])

  const fetchProduct = async () => {
    try {
      setLoading(true)
      const productData = await productService.getProduct(id)
      setProduct(productData)
      
      // Fetch recommendations
      try {
        const recs = await productService.getRecommendations(1, 4) // Mock user ID
        setRecommendations(recs)
      } catch (recError) {
        console.log('Failed to load recommendations')
      }
    } catch (error) {
      setError('Failed to load product details')
    } finally {
      setLoading(false)
    }
  }

  const handleAddToCart = async () => {
    try {
      await dispatch(addToCart({ productId: product.id, quantity })).unwrap()
      toast.success(`Added ${quantity} item(s) to cart!`)
    } catch (error) {
      toast.error('Failed to add to cart')
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

  if (error || !product) {
    return (
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Alert severity="error">{error || 'Product not found'}</Alert>
      </Container>
    )
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Grid container spacing={4}>
        {/* Product Images */}
        <Grid item xs={12} md={6}>
          <MotionBox
            initial={{ opacity: 0, x: -50 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <Paper elevation={3} sx={{ borderRadius: 3, overflow: 'hidden' }}>
              <img
                src={product.imageUrl || `https://via.placeholder.com/600x400?text=${product.name}`}
                alt={product.name}
                style={{
                  width: '100%',
                  height: '400px',
                  objectFit: 'cover'
                }}
              />
            </Paper>
          </MotionBox>
        </Grid>

        {/* Product Info */}
        <Grid item xs={12} md={6}>
          <MotionBox
            initial={{ opacity: 0, x: 50 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <Typography variant="h4" fontWeight="700" gutterBottom>
              {product.name}
            </Typography>

            <Box display="flex" alignItems="center" gap={2} mb={2}>
              <Rating value={product.rating || 4.5} readOnly precision={0.1} />
              <Typography variant="body2" color="text.secondary">
                ({product.reviewCount || 0} reviews)
              </Typography>
            </Box>

            <Typography variant="h3" color="primary" fontWeight="700" gutterBottom>
              ${product.basePrice}
            </Typography>

            <Box display="flex" gap={1} mb={3}>
              {product.featured && (
                <Chip label="Featured" color="secondary" />
              )}
              <Chip 
                label={product.stockQuantity > 0 ? 'In Stock' : 'Out of Stock'} 
                color={product.stockQuantity > 0 ? 'success' : 'error'} 
              />
            </Box>

            <Typography variant="body1" paragraph sx={{ lineHeight: 1.8 }}>
              {product.description}
            </Typography>

            <Divider sx={{ my: 3 }} />

            {/* Quantity and Add to Cart */}
            <Box display="flex" alignItems="center" gap={2} mb={3}>
              <Typography variant="body1" fontWeight="600">
                Quantity:
              </Typography>
              <TextField
                type="number"
                value={quantity}
                onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
                inputProps={{ min: 1, max: product.stockQuantity }}
                size="small"
                sx={{ width: 80 }}
              />
              <Typography variant="body2" color="text.secondary">
                ({product.stockQuantity} available)
              </Typography>
            </Box>

            <Box display="flex" gap={2} mb={3}>
              <Button
                variant="contained"
                size="large"
                startIcon={<ShoppingCart />}
                onClick={handleAddToCart}
                disabled={product.stockQuantity === 0}
                sx={{ flex: 1, py: 1.5 }}
              >
                Add to Cart
              </Button>
              <Button
                variant="outlined"
                size="large"
                startIcon={<Favorite />}
                sx={{ minWidth: 'auto', px: 2 }}
              >
                Wishlist
              </Button>
              <Button
                variant="outlined"
                size="large"
                startIcon={<Share />}
                sx={{ minWidth: 'auto', px: 2 }}
              >
                Share
              </Button>
            </Box>

            {/* Product Details */}
            <Paper elevation={1} sx={{ p: 3, borderRadius: 2 }}>
              <Typography variant="h6" fontWeight="600" gutterBottom>
                Product Details
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <Typography variant="body2" color="text.secondary">
                    Brand
                  </Typography>
                  <Typography variant="body1" fontWeight="500">
                    {product.brand || 'Generic'}
                  </Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography variant="body2" color="text.secondary">
                    Category
                  </Typography>
                  <Typography variant="body1" fontWeight="500">
                    {product.category || 'General'}
                  </Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography variant="body2" color="text.secondary">
                    SKU
                  </Typography>
                  <Typography variant="body1" fontWeight="500">
                    {product.sku || `PRD-${product.id}`}
                  </Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography variant="body2" color="text.secondary">
                    Availability
                  </Typography>
                  <Typography variant="body1" fontWeight="500">
                    {product.stockQuantity > 0 ? 'In Stock' : 'Out of Stock'}
                  </Typography>
                </Grid>
              </Grid>
            </Paper>
          </MotionBox>
        </Grid>
      </Grid>

      {/* Recommendations */}
      {recommendations.length > 0 && (
        <Box mt={8}>
          <Typography variant="h5" fontWeight="600" gutterBottom>
            You might also like
          </Typography>
          <Grid container spacing={3}>
            {recommendations.map((rec) => (
              <Grid item xs={12} sm={6} md={3} key={rec.id}>
                <Paper 
                  elevation={2} 
                  sx={{ 
                    p: 2, 
                    borderRadius: 2,
                    '&:hover': { boxShadow: 4 }
                  }}
                >
                  <img
                    src={rec.imageUrl || `https://via.placeholder.com/200x150?text=${rec.name}`}
                    alt={rec.name}
                    style={{
                      width: '100%',
                      height: '150px',
                      objectFit: 'cover',
                      borderRadius: '8px'
                    }}
                  />
                  <Typography variant="h6" noWrap sx={{ mt: 1 }}>
                    {rec.name}
                  </Typography>
                  <Typography variant="h6" color="primary" fontWeight="600">
                    ${rec.basePrice}
                  </Typography>
                </Paper>
              </Grid>
            ))}
          </Grid>
        </Box>
      )}
    </Container>
  )
}

export default ProductDetail