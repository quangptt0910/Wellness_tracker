package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.dto.cart.CartDto;
import com.example.wellnesstracker.dto.cart.UpdateCartDto;
import com.example.wellnesstracker.dto.cartItem.CartItemDto;
import com.example.wellnesstracker.dto.cartItem.CreateCartItemDto;
import com.example.wellnesstracker.model.Cart;
import com.example.wellnesstracker.model.CartItem;
import com.example.wellnesstracker.service.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(mapToCartDto(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemDto> addItemToCart(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody CreateCartItemDto request
    ) {
        String userId = jwt.getSubject();
        CartItem item = cartService.addItemToCart(
                userId,
                request.getSupplementId(),
                request.getQuantity(),
                request.getUnitPrice()
        );
        return new ResponseEntity<>(mapToCartItemDto(item), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CartDto> updateCart(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UpdateCartDto updateDto
    ) {
        String userId = jwt.getSubject();
        Cart updatedCart = cartService.updateCart(userId, updateDto.getItems());
        return ResponseEntity.ok(mapToCartDto(updatedCart));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String itemId,
            @RequestParam int quantity
    ) {
        String userId = jwt.getSubject();
        CartItem updatedItem = cartService.updateItemQuantity(userId, itemId, quantity);
        return ResponseEntity.ok(mapToCartItemDto(updatedItem));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String itemId
    ) {
        String userId = jwt.getSubject();
        cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    // Mapping methods
    private CartDto mapToCartDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalAmount(cart.getTotalAmount());
        dto.setItems(cart.getItems().stream()
                .map(this::mapToCartItemDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private CartItemDto mapToCartItemDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setSupplementId(item.getSupplementId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}