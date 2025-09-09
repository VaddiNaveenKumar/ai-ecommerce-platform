import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import {
  Container,
  Typography,
  Box,
  Grid,
  Card,
  CardMedia,
  CardContent,
  Button,
  Chip,
  Paper,
  CircularProgress
} from '@mui/material'
import { motion } from 'framer-motion'
import { productService } from '@services/productService'
import { toast } from 'react-hot-toast'

const MotionBox = motion(Box)
const MotionCard = motion(Card)

const Home = () => {
  const [featuredProducts, setFeaturedProducts] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchFeaturedProducts()
  }, [])

  const fetchFeaturedProducts = async () => {
    try {
      const products = await productService.getFeaturedProducts()
      setFeaturedProducts(products.slice(0, 8)) // Show only 8 products
    } catch (error) {
      toast.error('Failed to load featured products')
    } finally {
      setLoading(false)
    }
  }

  return (
    <Box>
      {/* Hero Section */}
      <Paper
        sx={{
          background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
          color: 'white',
          py: 8,
          mb: 6
        }}
      >
        <Container maxWidth="lg">
          <MotionBox
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            textAlign="center"
          >
            <Typography variant="h2" component="h1" gutterBottom fontWeight="bold">
              AI-Powered Shopping Experience
            </Typography>
            <Typography variant="h5" sx={{ mb: 4, opacity: 0.9 }}>
              Discover personalized products with intelligent recommendations
            </Typography>
            <Button
              variant="contained"
              size="large"
              component={Link}
              to="/products"
              sx={{
                bgcolor: 'white',
                color: 'primary.main',
                px: 4,
                py: 1.5,
                fontSize: '1.1rem',
                '&:hover': {
                  bgcolor: 'grey.100'
                }
              }}
            >
              Shop Now
            </Button>
          </MotionBox>
        </Container>
      </Paper>

      <Container maxWidth="lg">
        {/* Features Section */}
        <MotionBox
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.3, duration: 0.8 }}
          sx={{ mb: 8 }}
        >
          <Typography variant="h4" textAlign="center" gutterBottom fontWeight="600">
            Why Choose Us?
          </Typography>
          <Grid container spacing={4} sx={{ mt: 2 }}>
            {[
              {
                title: 'AI Recommendations',
                description: 'Get personalized product suggestions powered by machine learning',
                icon: '🤖'
              },
              {
                title: 'Dynamic Pricing',
                description: 'Best prices with real-time market analysis',
                icon: '💰'
              },
              {
                title: 'Fraud Protection',
                description: 'Advanced security with AI-powered fraud detection',
                icon: '🛡️'
              },
              {
                title: 'Fast Delivery',
                description: 'Quick and reliable shipping with delivery predictions',
                icon: '🚚'
              }
            ].map((feature, index) => (
              <Grid item xs={12} sm={6} md={3} key={index}>
                <MotionCard
                  initial={{ opacity: 0, y: 30 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: 0.1 * index, duration: 0.6 }}
                  sx={{ 
                    textAlign: 'center', 
                    p: 3, 
                    height: '100%',
                    '&:hover': {
                      transform: 'translateY(-4px)',
                      transition: 'transform 0.2s ease-in-out'
                    }
                  }}
                >
                  <Typography variant="h2" sx={{ mb: 2 }}>
                    {feature.icon}
                  </Typography>
                  <Typography variant="h6" gutterBottom fontWeight="600">
                    {feature.title}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {feature.description}
                  </Typography>
                </MotionCard>
              </Grid>
            ))}
          </Grid>
        </MotionBox>

        {/* Featured Products Section */}
        <MotionBox
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5, duration: 0.8 }}
        >
          <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
            <Typography variant="h4" fontWeight="600">
              Featured Products
            </Typography>
            <Button component={Link} to="/products" variant="outlined">
              View All
            </Button>
          </Box>

          {loading ? (
            <Box display="flex" justifyContent="center" py={8}>
              <CircularProgress />
            </Box>
          ) : (
            <Grid container spacing={3}>
              {featuredProducts.map((product, index) => (
                <Grid item xs={12} sm={6} md={3} key={product.id}>
                  <MotionCard
                    initial={{ opacity: 0, scale: 0.9 }}
                    animate={{ opacity: 1, scale: 1 }}
                    transition={{ delay: 0.1 * index, duration: 0.5 }}
                    sx={{
                      height: '100%',
                      display: 'flex',
                      flexDirection: 'column',
                      '&:hover': {
                        transform: 'translateY(-4px)',
                        boxShadow: 4,
                        transition: 'all 0.2s ease-in-out'
                      }
                    }}
                  >
                    <CardMedia
                      component="img"
                      height="200"
                      image={product.imageUrl || `https://via.placeholder.com/300x200?text=${product.name}`}
                      alt={product.name}
                    />
                    <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                      <Typography gutterBottom variant="h6" component="h3" noWrap>
                        {product.name}
                      </Typography>
                      <Typography variant="body2" color="text.secondary" sx={{ mb: 2, flexGrow: 1 }}>
                        {product.description?.substring(0, 100)}...
                      </Typography>
                      <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                        <Typography variant="h6" color="primary" fontWeight="600">
                          ${product.basePrice}
                        </Typography>
                        {product.featured && (
                          <Chip label="Featured" color="secondary" size="small" />
                        )}
                      </Box>
                      <Button
                        variant="contained"
                        fullWidth
                        component={Link}
                        to={`/products/${product.id}`}
                      >
                        View Details
                      </Button>
                    </CardContent>
                  </MotionCard>
                </Grid>
              ))}
            </Grid>
          )}
        </MotionBox>
      </Container>
    </Box>
  )
}

export default Home