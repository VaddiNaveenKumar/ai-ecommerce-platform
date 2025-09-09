import api from './api'

export const productService = {
  // Get all products
  getProducts: async (params = {}) => {
    const response = await api.get('/api/products', { params })
    return response.data
  },

  // Get product by ID
  getProduct: async (id) => {
    const response = await api.get(`/api/products/${id}`)
    return response.data
  },

  // Search products
  searchProducts: async (query, filters = {}) => {
    const params = { q: query, ...filters }
    const response = await api.get('/api/search', { params })
    return response.data
  },

  // Get search suggestions
  getSearchSuggestions: async (query) => {
    const response = await api.get('/api/search/suggestions', {
      params: { q: query }
    })
    return response.data
  },

  // Get featured products
  getFeaturedProducts: async () => {
    const response = await api.get('/api/products/featured')
    return response.data
  },

  // Get recommendations
  getRecommendations: async (userId, limit = 5) => {
    const response = await api.get(`/api/ai/recommendations/${userId}`, {
      params: { limit }
    })
    return response.data
  },

  // Get dynamic pricing
  getDynamicPricing: async (productId) => {
    const response = await api.get(`/api/ai/pricing/${productId}`)
    return response.data
  },

  // Get categories
  getCategories: async () => {
    const response = await api.get('/api/categories')
    return response.data
  }
}