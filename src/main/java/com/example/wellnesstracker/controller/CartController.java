package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.dto.cart.CartDto;
import com.example.wellnesstracker.dto.cart.CreateCartDto;
import com.example.wellnesstracker.dto.cart.UpdateCartDto;
import com.example.wellnesstracker.dto.cartItem.CreateCartItemDto;
import com.example.wellnesstracker.model.Supplement;
import com.example.wellnesstracker.service.SupplementService;
import com.example.wellnesstracker.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final SupplementService supplementService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable String userId) {
        return cartService.getCartByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(@Valid @RequestBody CreateCartDto createCartDto) {
        try {
            CartDto cart = cartService.createCart(createCartDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<?> addItemToCart(
            @PathVariable String userId,
            @Valid @RequestBody CreateCartItemDto itemDto) {

        try {
            CartDto cart = cartService.addItemToCart(userId, itemDto);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/user/{userId}/items/{supplementId}")
    public ResponseEntity<CartDto> removeItemFromCart(
            @PathVariable String userId,
            @PathVariable String supplementId) {
        try {
            CartDto cart = cartService.removeItemFromCart(userId, supplementId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            System.err.println("Error removing item from cart: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/user/{userId}/items/{supplementId}")
    public ResponseEntity<CartDto> updateItemQuantity(
            @PathVariable String userId,
            @PathVariable String supplementId,
            @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null) {
                return ResponseEntity.badRequest().build();
            }
            CartDto cart = cartService.updateItemQuantity(userId, supplementId, quantity);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            System.err.println("Error updating item quantity: " + e.getMessage());
            e.printStackTrace();

            // Return proper error response based on error type
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<CartDto> updateCart(
            @PathVariable String userId,
            @Valid @RequestBody UpdateCartDto updateCartDto) {
        try {
            CartDto cart = cartService.updateCart(userId, updateCartDto);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete entire cart
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteCart(@PathVariable String userId) {
        cartService.deleteCart(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get cart item count
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Integer>> getCartItemCount(@PathVariable String userId) {
        int count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(Map.of("itemCount", count));
    }

    /**
     * Get cart total amount
     */
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Map<String, BigDecimal>> getCartTotal(@PathVariable String userId) {
        BigDecimal total = cartService.getCartTotal(userId);
        return ResponseEntity.ok(Map.of("totalAmount", total));
    }

    /**
     * Check if cart exists for user
     */
    @GetMapping("/user/{userId}/exists")
    public ResponseEntity<Map<String, Boolean>> checkCartExists(@PathVariable String userId) {
        boolean exists = cartService.getCartByUserId(userId).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}