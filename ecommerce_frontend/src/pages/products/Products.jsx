import React, { useEffect, useState } from 'react'
import { useSearchParams, Link } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import {
  Container,
  Grid,
  Card,
  CardMedia,
  CardContent,
  Typography,
  Button,
  Box,
  Pagination,
  CircularProgress,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Chip,
  Paper,
  Slider
} from '@mui/material'
import { Search, FilterList } from '@mui/icons-material'
import { motion } from 'framer-motion'
import { fetchProducts, searchProducts } from '@store/slices/productsSlice'
import { addToCart } from '@store/slices/cartSlice'
import { toast } from 'react-hot-toast'

const MotionCard = motion(Card)

const Products = () => {
  const dispatch = useDispatch()
  const [searchParams, setSearchParams] = useSearchParams()
  const { products, searchResults, loading, totalPages, currentPage } = useSelector(state => state.products)
  
  const [searchQuery, setSearchQuery] = useState(searchParams.get('search') || '')
  const [sortBy, setSortBy] = useState('name')
  const [priceRange, setPriceRange] = useState([0, 5000])
  const [category, setCategory] = useState('')
  const [page, setPage] = useState(1)

  const isSearching = searchParams.get('search')
  const displayProducts = isSearching ? searchResults : products

  useEffect(() => {
    if (isSearching) {
      dispatch(searchProducts({
        query: searchQuery,
        filters: {
          page: page - 1,
          size: 12,
          sort: sortBy,
          minPrice: priceRange[0],
          maxPrice: priceRange[1],
          category
        }
      }))
    } else {
      dispatch(fetchProducts({
        page: page - 1,
        size: 12,
        sort: sortBy
      }))
    }
  }, [dispatch, searchQuery, page, sortBy, priceRange, category, isSearching])

  const handleSearch = () => {
    if (searchQuery.trim()) {
      setSearchParams({ search: searchQuery.trim() })
      setPage(1)
    } else {
      setSearchParams({})
    }
  }

  const handleAddToCart = async (productId) => {
    try {
      await dispatch(addToCart({ productId, quantity: 1 })).unwrap()
      toast.success('Added to cart!')
    } catch (error) {
      toast.error('Failed to add to cart')
    }
  }

  const handlePageChange = (event, value) => {
    setPage(value)
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      {/* Search and Filters */}
      <Paper elevation={2} sx={{ p: 3, mb: 4, borderRadius: 3 }}>
        <Grid container spacing={3} alignItems="center">
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              placeholder="Search products..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              InputProps={{
                startAdornment: <Search sx={{ mr: 1, color: 'action.active' }} />
              }}
            />
          </Grid>
          <Grid item xs={12} md={2}>
            <Button
              variant="contained"
              onClick={handleSearch}
              fullWidth
              sx={{ py: 1.5 }}
            >
              Search
            </Button>
          </Grid>
          <Grid item xs={12} md={2}>
            <FormControl fullWidth>
              <InputLabel>Sort By</InputLabel>
              <Select
                value={sortBy}
                label="Sort By"
                onChange={(e) => setSortBy(e.target.value)}
              >
                <MenuItem value="name">Name</MenuItem>
                <MenuItem value="price">Price</MenuItem>
                <MenuItem value="rating">Rating</MenuItem>
                <MenuItem value="newest">Newest</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12} md={4}>
            <Typography gutterBottom>Price Range</Typography>
            <Slider
              value={priceRange}
              onChange={(e, newValue) => setPriceRange(newValue)}
              valueLabelDisplay="auto"
              min={0}
              max={5000}
              step={50}
            />
            <Box display="flex" justifyContent="space-between">
              <Typography variant="caption">${priceRange[0]}</Typography>
              <Typography variant="caption">${priceRange[1]}</Typography>
            </Box>
          </Grid>
        </Grid>
      </Paper>

      {/* Results Header */}
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" fontWeight="600">
          {isSearching ? `Search Results for "${searchQuery}"` : 'All Products'}
        </Typography>
        {isSearching && (
          <Chip
            label={`${displayProducts.length} results`}
            color="primary"
            variant="outlined"
          />
        )}
      </Box>

      {/* Products Grid */}
      {loading ? (
        <Box display="flex" justifyContent="center" py={8}>
          <CircularProgress size={60} />
        </Box>
      ) : displayProducts.length === 0 ? (
        <Box textAlign="center" py={8}>
          <Typography variant="h6" color="text.secondary">
            No products found
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
            Try adjusting your search criteria
          </Typography>
        </Box>
      ) : (
        <>
          <Grid container spacing={3}>
            {displayProducts.map((product, index) => (
              <Grid item xs={12} sm={6} md={4} lg={3} key={product.id}>
                <MotionCard
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: index * 0.1, duration: 0.5 }}
                  sx={{
                    height: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    '&:hover': {
                      transform: 'translateY(-4px)',
                      boxShadow: 6,
                      transition: 'all 0.2s ease-in-out'
                    }
                  }}
                >
                  <CardMedia
                    component="img"
                    height="220"
                    image={product.imageUrl || `https://via.placeholder.com/300x220?text=${product.name}`}
                    alt={product.name}
                    sx={{ objectFit: 'cover' }}
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
                      {product.rating && (
                        <Box display="flex" alignItems="center">
                          <Typography variant="body2" color="text.secondary">
                            ⭐ {product.rating}
                          </Typography>
                        </Box>
                      )}
                    </Box>

                    <Box display="flex" gap={1}>
                      <Button
                        variant="outlined"
                        size="small"
                        component={Link}
                        to={`/products/${product.id}`}
                        sx={{ flex: 1 }}
                      >
                        View
                      </Button>
                      <Button
                        variant="contained"
                        size="small"
                        onClick={() => handleAddToCart(product.id)}
                        disabled={product.stockQuantity === 0}
                        sx={{ flex: 1 }}
                      >
                        {product.stockQuantity === 0 ? 'Out of Stock' : 'Add to Cart'}
                      </Button>
                    </Box>
                  </CardContent>
                </MotionCard>
              </Grid>
            ))}
          </Grid>

          {/* Pagination */}
          {totalPages > 1 && (
            <Box display="flex" justifyContent="center" mt={6}>
              <Pagination
                count={totalPages}
                page={page}
                onChange={handlePageChange}
                color="primary"
                size="large"
                showFirstButton
                showLastButton
              />
            </Box>
          )}
        </>
      )}
    </Container>
  )
}

export default Products