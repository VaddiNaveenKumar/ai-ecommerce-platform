import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { productService } from '@services/productService'

// Async thunks
export const fetchProducts = createAsyncThunk(
  'products/fetchProducts',
  async (params = {}, { rejectWithValue }) => {
    try {
      return await productService.getProducts(params)
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message)
    }
  }
)

export const searchProducts = createAsyncThunk(
  'products/searchProducts',
  async ({ query, filters }, { rejectWithValue }) => {
    try {
      return await productService.searchProducts(query, filters)
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message)
    }
  }
)

export const fetchFeaturedProducts = createAsyncThunk(
  'products/fetchFeaturedProducts',
  async (_, { rejectWithValue }) => {
    try {
      return await productService.getFeaturedProducts()
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message)
    }
  }
)

const productsSlice = createSlice({
  name: 'products',
  initialState: {
    products: [],
    featuredProducts: [],
    searchResults: [],
    categories: [],
    loading: false,
    searchLoading: false,
    error: null,
    totalPages: 0,
    currentPage: 0,
    searchQuery: ''
  },
  reducers: {
    clearProducts: (state) => {
      state.products = []
      state.searchResults = []
    },
    setSearchQuery: (state, action) => {
      state.searchQuery = action.payload
    },
    clearError: (state) => {
      state.error = null
    }
  },
  extraReducers: (builder) => {
    builder
      // Fetch products
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false
        state.products = action.payload.content || []
        state.totalPages = action.payload.totalPages || 0
        state.currentPage = action.payload.number || 0
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Search products
      .addCase(searchProducts.pending, (state) => {
        state.searchLoading = true
        state.error = null
      })
      .addCase(searchProducts.fulfilled, (state, action) => {
        state.searchLoading = false
        state.searchResults = action.payload.content || []
        state.totalPages = action.payload.totalPages || 0
        state.currentPage = action.payload.number || 0
      })
      .addCase(searchProducts.rejected, (state, action) => {
        state.searchLoading = false
        state.error = action.payload
      })
      // Fetch featured products
      .addCase(fetchFeaturedProducts.fulfilled, (state, action) => {
        state.featuredProducts = action.payload || []
      })
  }
})

export const { clearProducts, setSearchQuery, clearError } = productsSlice.actions
export default productsSlice.reducer