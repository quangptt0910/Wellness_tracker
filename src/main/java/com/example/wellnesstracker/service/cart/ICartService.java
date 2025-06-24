package com.example.wellnesstracker.service.cart;

import com.example.wellnesstracker.dto.cart.CartDto;
import com.example.wellnesstracker.dto.cart.CreateCartDto;
import com.example.wellnesstracker.dto.cart.UpdateCartDto;

import java.math.BigDecimal;

public interface ICartService {
    CartDto getCartByUser(String userId);
    CartDto createCart(CreateCartDto cartDto);
    CartDto updateCart(UpdateCartDto cartDto);
    void deleteCart(String userId);
    void clearCart(String id);
    BigDecimal getTotalPrice(String id);
}
