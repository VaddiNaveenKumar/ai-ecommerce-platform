import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const Dashboard = () => {
  const { user, logout, token } = useAuth();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/users/profile', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setProfile(response.data);
    } catch (error) {
      console.error('Failed to fetch profile:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div style={styles.container}>
        <div style={styles.loading}>🔄 Loading...</div>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>🎉 Welcome to AI E-commerce!</h1>
        <button onClick={logout} style={styles.logoutBtn}>
          🚪 Logout
        </button>
      </div>

      <div style={styles.card}>
        <h2 style={styles.cardTitle}>👤 Your Profile</h2>
        {profile && (
          <div style={styles.profileInfo}>
            <div style={styles.infoRow}>
              <strong>Username:</strong> {profile.username}
            </div>
            <div style={styles.infoRow}>
              <strong>Email:</strong> {profile.email}
            </div>
            <div style={styles.infoRow}>
              <strong>Name:</strong> {profile.firstName} {profile.lastName}
            </div>
            <div style={styles.infoRow}>
              <strong>Phone:</strong> {profile.phone}
            </div>
            <div style={styles.infoRow}>
              <strong>Role:</strong> <span style={styles.role}>{profile.role}</span>
            </div>
          </div>
        )}
      </div>

      <div style={styles.features}>
        <h3 style={styles.featuresTitle}>🚀 Available Features</h3>
        <div style={styles.featureGrid}>
          <div style={styles.featureCard}>
            <div style={styles.featureIcon}>🛍️</div>
            <h4>Products</h4>
            <p>Browse AI-powered product catalog</p>
          </div>
          <div style={styles.featureCard}>
            <div style={styles.featureIcon}>🛒</div>
            <h4>Shopping Cart</h4>
            <p>Smart cart with recommendations</p>
          </div>
          <div style={styles.featureCard}>
            <div style={styles.featureIcon}>🤖</div>
            <h4>AI Recommendations</h4>
            <p>Personalized product suggestions</p>
          </div>
          <div style={styles.featureCard}>
            <div style={styles.featureIcon}>📊</div>
            <h4>Analytics</h4>
            <p>AI-powered insights and trends</p>
          </div>
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    padding: '20px',
    maxWidth: '1200px',
    margin: '0 auto'
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '30px'
  },
  title: {
    color: '#333',
    fontSize: '28px'
  },
  logoutBtn: {
    padding: '10px 20px',
    backgroundColor: '#dc3545',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    cursor: 'pointer'
  },
  card: {
    backgroundColor: 'white',
    padding: '30px',
    borderRadius: '12px',
    boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
    marginBottom: '30px'
  },
  cardTitle: {
    marginBottom: '20px',
    color: '#333'
  },
  profileInfo: {
    display: 'flex',
    flexDirection: 'column',
    gap: '10px'
  },
  infoRow: {
    padding: '10px 0',
    borderBottom: '1px solid #eee'
  },
  role: {
    backgroundColor: '#007bff',
    color: 'white',
    padding: '4px 8px',
    borderRadius: '4px',
    fontSize: '12px'
  },
  features: {
    backgroundColor: 'white',
    padding: '30px',
    borderRadius: '12px',
    boxShadow: '0 4px 20px rgba(0,0,0,0.1)'
  },
  featuresTitle: {
    marginBottom: '20px',
    color: '#333'
  },
  featureGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '20px'
  },
  featureCard: {
    padding: '20px',
    border: '2px solid #f0f0f0',
    borderRadius: '8px',
    textAlign: 'center'
  },
  featureIcon: {
    fontSize: '32px',
    marginBottom: '10px'
  },
  loading: {
    textAlign: 'center',
    fontSize: '18px',
    marginTop: '50px'
  }
};

export default Dashboard;