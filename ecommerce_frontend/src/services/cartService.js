import api from './api'

export const cartService = {
  // Get user cart
  getCart: async () => {
    const response = await api.get('/api/cart')
    return response.data
  },

  // Add item to cart
  addItem: async (productId, quantity = 1) => {
    const response = await api.post('/api/cart/items', {
      productId,
      quantity
    })
    return response.data
  },

  // Update cart item quantity
  updateItem: async (itemId, quantity) => {
    const response = await api.put(`/api/cart/items/${itemId}`, {
      quantity
    })
    return response.data
  },

  // Remove cart item
  removeItem: async (itemId) => {
    await api.delete(`/api/cart/items/${itemId}`)
  },

  // Clear entire cart
  clearCart: async () => {
    await api.delete('/api/cart')
  },

  // Apply coupon
  applyCoupon: async (couponCode) => {
    const response = await api.post('/api/cart/coupon', {
      couponCode
    })
    return response.data
  },

  // Remove coupon
  removeCoupon: async () => {
    await api.delete('/api/cart/coupon')
  }
}