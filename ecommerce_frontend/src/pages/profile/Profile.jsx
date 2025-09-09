import React, { useState } from 'react'
import { useAuth } from '@/contexts/AuthContext'
import {
  Container,
  Typography,
  Box,
  Paper,
  Grid,
  TextField,
  Button,
  Avatar,
  Divider,
  Alert
} from '@mui/material'
import { Person, Email, Phone, Edit, Save, Cancel } from '@mui/icons-material'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { motion } from 'framer-motion'
import { authService } from '@services/authService'
import { toast } from 'react-hot-toast'

const MotionPaper = motion(Paper)

const schema = yup.object({
  firstName: yup.string().required('First name is required'),
  lastName: yup.string().required('Last name is required'),
  email: yup.string().email('Invalid email').required('Email is required'),
  phone: yup.string().required('Phone number is required')
})

const Profile = () => {
  const { user, updateUser } = useAuth()
  const [editing, setEditing] = useState(false)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm({
    resolver: yupResolver(schema),
    defaultValues: {
      firstName: user?.firstName || '',
      lastName: user?.lastName || '',
      email: user?.email || '',
      phone: user?.phone || ''
    }
  })

  const onSubmit = async (data) => {
    try {
      setLoading(true)
      setError('')
      
      await authService.updateProfile(data)
      updateUser(data)
      
      toast.success('Profile updated successfully!')
      setEditing(false)
    } catch (error) {
      setError(error.response?.data?.message || 'Failed to update profile')
    } finally {
      setLoading(false)
    }
  }

  const handleCancel = () => {
    reset({
      firstName: user?.firstName || '',
      lastName: user?.lastName || '',
      email: user?.email || '',
      phone: user?.phone || ''
    })
    setEditing(false)
    setError('')
  }

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Typography variant="h4" fontWeight="700" gutterBottom>
        My Profile
      </Typography>

      <MotionPaper
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        elevation={3}
        sx={{ p: 4, borderRadius: 3 }}
      >
        {/* Profile Header */}
        <Box display="flex" alignItems="center" mb={4}>
          <Avatar
            sx={{
              width: 80,
              height: 80,
              fontSize: '2rem',
              bgcolor: 'primary.main',
              mr: 3
            }}
          >
            {user?.firstName?.[0] || user?.username?.[0] || 'U'}
          </Avatar>
          <Box>
            <Typography variant="h5" fontWeight="600">
              {user?.firstName} {user?.lastName}
            </Typography>
            <Typography variant="body1" color="text.secondary">
              @{user?.username}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              Member since {user?.createdAt ? new Date(user.createdAt).getFullYear() : '2024'}
            </Typography>
          </Box>
        </Box>

        <Divider sx={{ mb: 4 }} />

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        {/* Profile Form */}
        <form onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={3}>
            <Grid item xs={12} sm={6}>
              <TextField
                {...register('firstName')}
                label="First Name"
                fullWidth
                disabled={!editing}
                error={!!errors.firstName}
                helperText={errors.firstName?.message}
                InputProps={{
                  startAdornment: <Person sx={{ mr: 1, color: 'action.active' }} />
                }}
              />
            </Grid>
            
            <Grid item xs={12} sm={6}>
              <TextField
                {...register('lastName')}
                label="Last Name"
                fullWidth
                disabled={!editing}
                error={!!errors.lastName}
                helperText={errors.lastName?.message}
                InputProps={{
                  startAdornment: <Person sx={{ mr: 1, color: 'action.active' }} />
                }}
              />
            </Grid>
            
            <Grid item xs={12}>
              <TextField
                {...register('email')}
                label="Email"
                type="email"
                fullWidth
                disabled={!editing}
                error={!!errors.email}
                helperText={errors.email?.message}
                InputProps={{
                  startAdornment: <Email sx={{ mr: 1, color: 'action.active' }} />
                }}
              />
            </Grid>
            
            <Grid item xs={12}>
              <TextField
                {...register('phone')}
                label="Phone Number"
                fullWidth
                disabled={!editing}
                error={!!errors.phone}
                helperText={errors.phone?.message}
                InputProps={{
                  startAdornment: <Phone sx={{ mr: 1, color: 'action.active' }} />
                }}
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                label="Username"
                value={user?.username || ''}
                fullWidth
                disabled
                helperText="Username cannot be changed"
              />
            </Grid>
          </Grid>

          {/* Action Buttons */}
          <Box display="flex" justifyContent="flex-end" gap={2} mt={4}>
            {editing ? (
              <>
                <Button
                  variant="outlined"
                  onClick={handleCancel}
                  startIcon={<Cancel />}
                  disabled={loading}
                >
                  Cancel
                </Button>
                <Button
                  type="submit"
                  variant="contained"
                  startIcon={<Save />}
                  disabled={loading}
                >
                  {loading ? 'Saving...' : 'Save Changes'}
                </Button>
              </>
            ) : (
              <Button
                variant="contained"
                onClick={() => setEditing(true)}
                startIcon={<Edit />}
              >
                Edit Profile
              </Button>
            )}
          </Box>
        </form>
      </MotionPaper>

      {/* Account Stats */}
      <Grid container spacing={3} sx={{ mt: 4 }}>
        <Grid item xs={12} sm={4}>
          <MotionPaper
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.1 }}
            elevation={2}
            sx={{ p: 3, textAlign: 'center', borderRadius: 3 }}
          >
            <Typography variant="h4" color="primary" fontWeight="700">
              0
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Total Orders
            </Typography>
          </MotionPaper>
        </Grid>
        
        <Grid item xs={12} sm={4}>
          <MotionPaper
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.2 }}
            elevation={2}
            sx={{ p: 3, textAlign: 'center', borderRadius: 3 }}
          >
            <Typography variant="h4" color="primary" fontWeight="700">
              $0.00
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Total Spent
            </Typography>
          </MotionPaper>
        </Grid>
        
        <Grid item xs={12} sm={4}>
          <MotionPaper
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3 }}
            elevation={2}
            sx={{ p: 3, textAlign: 'center', borderRadius: 3 }}
          >
            <Typography variant="h4" color="primary" fontWeight="700">
              0
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Wishlist Items
            </Typography>
          </MotionPaper>
        </Grid>
      </Grid>
    </Container>
  )
}

export default Profile