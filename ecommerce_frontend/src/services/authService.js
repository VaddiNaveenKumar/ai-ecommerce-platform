import api from './api'

export const authService = {
  // Register user
  register: async (userData) => {
    const response = await api.post('/api/auth/register', userData)
    return response.data
  },

  // Login user
  login: async (credentials) => {
    const response = await api.post('/api/auth/login', credentials)
    const { token, ...user } = response.data
    
    if (token) {
      localStorage.setItem('authToken', token)
      localStorage.setItem('user', JSON.stringify(user))
    }
    
    return response.data
  },

  // Logout user
  logout: () => {
    localStorage.removeItem('authToken')
    localStorage.removeItem('user')
  },

  // Get current user
  getCurrentUser: () => {
    const user = localStorage.getItem('user')
    return user ? JSON.parse(user) : null
  },

  // Check if user is authenticated
  isAuthenticated: () => {
    return !!localStorage.getItem('authToken')
  },

  // Get user profile
  getProfile: async () => {
    const response = await api.get('/api/users/profile')
    return response.data
  },

  // Update user profile
  updateProfile: async (profileData) => {
    const response = await api.put('/api/users/profile', profileData)
    return response.data
  }
}