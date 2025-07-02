package com.example.wellnesstracker.service.cart;

import com.example.wellnesstracker.dto.cart.CartDto;
import com.example.wellnesstracker.dto.cart.CreateCartDto;
import com.example.wellnesstracker.dto.cart.UpdateCartDto;
import com.example.wellnesstracker.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartByUserId(String userId);
    CartDto getCartByUser(String userId);
    CartDto createCart(CreateCartDto cartDto);
    CartDto updateCart(UpdateCartDto cartDto);

    void deleteCart(String userId);
    void clearCart(String id);
    BigDecimal getTotalPrice(String id);
}
