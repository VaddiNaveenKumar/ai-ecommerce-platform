package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Cart;
import com.ecommerce.service.CartService;
import com.ecommerce.service.dto.CartItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping cart and wishlist management")
public class CartController {
    
    private final CartService cartService;
    
    @GetMapping("/{userId}")
    @Operation(summary = "Get user cart")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/{userId}/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<Cart> addItem(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemDto cartItemDto) {
        
        Cart cart = cartService.addItem(userId, cartItemDto);
        return ResponseEntity.ok(cart);
    }
    
    @PutMapping("/{userId}/items/{productId}")
    @Operation(summary = "Update item quantity")
    public ResponseEntity<Cart> updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        
        Cart cart = cartService.updateItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/{userId}/items/{productId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<Cart> removeItem(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        
        Cart cart = cartService.removeItem(userId, productId);
        return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/{userId}")
    @Operation(summary = "Clear cart")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{userId}/items/{productId}/wishlist")
    @Operation(summary = "Move item to wishlist")
    public ResponseEntity<Cart> moveToWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        
        Cart cart = cartService.moveToWishlist(userId, productId);
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/{userId}/coupon")
    @Operation(summary = "Apply coupon")
    public ResponseEntity<Map<String, String>> applyCoupon(
            @PathVariable Long userId,
            @RequestParam String couponCode) {
        
        cartService.applyCoupon(userId, couponCode);
        return ResponseEntity.ok(Map.of("message", "Coupon applied successfully"));
    }
    
    @DeleteMapping("/{userId}/coupon")
    @Operation(summary = "Remove coupon")
    public ResponseEntity<Map<String, String>> removeCoupon(@PathVariable Long userId) {
        cartService.removeCoupon(userId);
        return ResponseEntity.ok(Map.of("message", "Coupon removed successfully"));
    }
}