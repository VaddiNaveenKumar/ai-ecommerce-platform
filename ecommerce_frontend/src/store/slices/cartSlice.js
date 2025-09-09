import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { cartService } from '@services/cartService'

// Async thunks
export const fetchCart = createAsyncThunk(
  'cart/fetchCart',
  async (_, { rejectWithValue }) => {
    try {
      return await cartService.getCart()
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message)
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
      return rejectWithValue(error.response?.data || error.message)
    }
  }
)

export const updateCartItem = createAsyncThunk(
  'cart/updateCartItem',
  async ({ itemId, quantity }, { rejectWithValue }) => {
    try {
      await cartService.updateItem(itemId, quantity)
      return await cartService.getCart()
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message)
    }
  }
)

export const removeCartItem = createAsyncThunk(
  'cart/removeCartItem',
  async (itemId, { rejectWithValue }) => {
    try {
      await cartService.removeItem(itemId)
      return await cartService.getCart()
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message)
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
    },
    clearError: (state) => {
      state.error = null
    }
  },
  extraReducers: (builder) => {
    builder
      // Fetch cart
      .addCase(fetchCart.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchCart.fulfilled, (state, action) => {
        state.loading = false
        state.items = action.payload.items || []
        state.totalAmount = action.payload.totalAmount || 0
        state.itemCount = action.payload.itemCount || 0
      })
      .addCase(fetchCart.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload
      })
      // Add to cart
      .addCase(addToCart.fulfilled, (state, action) => {
        state.items = action.payload.items || []
        state.totalAmount = action.payload.totalAmount || 0
        state.itemCount = action.payload.itemCount || 0
      })
      // Update cart item
      .addCase(updateCartItem.fulfilled, (state, action) => {
        state.items = action.payload.items || []
        state.totalAmount = action.payload.totalAmount || 0
        state.itemCount = action.payload.itemCount || 0
      })
      // Remove cart item
      .addCase(removeCartItem.fulfilled, (state, action) => {
        state.items = action.payload.items || []
        state.totalAmount = action.payload.totalAmount || 0
        state.itemCount = action.payload.itemCount || 0
      })
  }
})

export const { clearCart, clearError } = cartSlice.actions
export default cartSlice.reducer