#!/bin/bash

# Load Testing Script for AI E-commerce Platform

echo "Starting Load Testing..."

# Test 1: Health Check Load Test
echo "Testing Health Endpoint..."
ab -n 1000 -c 10 http://localhost:8080/api/health

# Test 2: Product API Load Test
echo "Testing Products Endpoint..."
ab -n 500 -c 5 http://localhost:8080/api/products

# Test 3: Search API Load Test
echo "Testing Search Endpoint..."
ab -n 300 -c 3 "http://localhost:8080/api/search?q=laptop"

# Test 4: AI Recommendations Load Test
echo "Testing AI Recommendations..."
ab -n 200 -c 2 http://localhost:8080/api/ai/recommendations/1

echo "Load Testing Complete!"
echo "Check the results above for performance metrics."