import api from './api'

export const orderService = {
  // Create order
  createOrder: async (orderData) => {
    const response = await api.post('/api/orders', orderData)
    return response.data
  },

  // Get user orders
  getUserOrders: async (userId, params = {}) => {
    const response = await api.get(`/api/orders/user/${userId}`, { params })
    return response.data
  },

  // Get order by ID
  getOrder: async (orderId) => {
    const response = await api.get(`/api/orders/${orderId}`)
    return response.data
  },

  // Get order by order number
  getOrderByNumber: async (orderNumber) => {
    const response = await api.get(`/api/orders/number/${orderNumber}`)
    return response.data
  },

  // Cancel order
  cancelOrder: async (orderId) => {
    const response = await api.post(`/api/orders/${orderId}/cancel`)
    return response.data
  },

  // Get delivery prediction
  getDeliveryPrediction: async (orderId) => {
    const response = await api.get(`/api/orders/${orderId}/delivery-prediction`)
    return response.data
  }
}