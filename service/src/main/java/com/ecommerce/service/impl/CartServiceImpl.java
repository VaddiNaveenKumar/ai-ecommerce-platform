package com.ecommerce.service.impl;

import com.ecommerce.core.entity.*;
import com.ecommerce.core.repository.*;
import com.ecommerce.service.CartService;
import com.ecommerce.service.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    @Override
    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
                
                Cart cart = new Cart();
                cart.setUser(user);
                cart.setCartItems(new HashSet<>());
                return cartRepository.save(cart);
            });
    }
    
    @Override
    public Cart addItem(Long userId, CartItemDto cartItemDto) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(cartItemDto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        // Check if item already exists
        Optional<CartItem> existingItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(cartItemDto.getProductId()))
            .findFirst();
        
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                existingItem.get().getQuantity() + cartItemDto.getQuantity()
            );
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setSavedForLater(cartItemDto.isSavedForLater());
            cart.getCartItems().add(cartItem);
        }
        
        return cartRepository.save(cart);
    }
    
    @Override
    public Cart updateItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .ifPresent(item -> item.setQuantity(quantity));
        
        return cartRepository.save(cart);
    }
    
    @Override
    public Cart removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getCartItems().removeIf(item -> 
            item.getProduct().getId().equals(productId)
        );
        
        return cartRepository.save(cart);
    }
    
    @Override
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
    
    @Override
    public Cart moveToWishlist(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .ifPresent(item -> item.setSavedForLater(true));
        
        return cartRepository.save(cart);
    }
    
    @Override
    public Cart moveFromWishlist(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .ifPresent(item -> item.setSavedForLater(false));
        
        return cartRepository.save(cart);
    }
    
    @Override
    public void applyCoupon(Long userId, String couponCode) {
        Cart cart = getOrCreateCart(userId);
        cart.setAppliedCoupons(couponCode);
        cartRepository.save(cart);
    }
    
    @Override
    public void removeCoupon(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.setAppliedCoupons(null);
        cartRepository.save(cart);
    }
}