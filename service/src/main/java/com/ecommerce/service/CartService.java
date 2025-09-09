package com.ecommerce.service;

import com.ecommerce.core.entity.Cart;
import com.ecommerce.core.entity.CartItem;
import com.ecommerce.service.dto.CartItemDto;

import java.util.Optional;

public interface CartService {
    
    Cart getOrCreateCart(Long userId);
    Cart addItem(Long userId, CartItemDto cartItemDto);
    Cart updateItemQuantity(Long userId, Long productId, Integer quantity);
    Cart removeItem(Long userId, Long productId);
    void clearCart(Long userId);
    
    Cart moveToWishlist(Long userId, Long productId);
    Cart moveFromWishlist(Long userId, Long productId);
    
    void applyCoupon(Long userId, String couponCode);
    void removeCoupon(Long userId);
}