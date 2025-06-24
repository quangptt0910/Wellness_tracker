package com.example.wellnesstracker.service.cart;

import com.example.wellnesstracker.dto.cart.CartDto;
import com.example.wellnesstracker.dto.cart.CreateCartDto;
import com.example.wellnesstracker.dto.cart.UpdateCartDto;
import com.example.wellnesstracker.dto.cartItem.CartItemDto;
import com.example.wellnesstracker.model.Cart;
import com.example.wellnesstracker.model.CartItem;
import com.example.wellnesstracker.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService{

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartDto getCartByUser(String userId) {
        return null;
    }

    @Override
    public CartDto createCart(CreateCartDto cartDto) {
        Cart cart = new Cart();
        cart.setUserId(cartDto.getUserId());
        cart.setItems(new ArrayList<>());
        if (cartDto.getItems() != null) {
            cartDto.getItems().forEach(item -> {
                CartItem cartItem = new CartItem();
                cartItem.setSupplementId(item.getSupplementId());
                cartItem.setQuantity(item.getQuantity());
                cartItem.setUnitPrice(item.getUnitPrice());
                cart.addItem(cartItem);
            });
        }

        Cart savedCart = cartRepository.save(cart);
        return toDto(savedCart);
    }

    @Override
    public CartDto updateCart(UpdateCartDto cartDto) {
        return null;
    }

    @Override
    public void deleteCart(String userId) {

    }

    @Override
    public void clearCart(String id) {

    }

    @Override
    public BigDecimal getTotalPrice(String id) {
        return null;
    }


    public CartDto toDto(Cart cart) {
        if (cart == null) return null;

        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalAmount(cart.getTotalAmount());

        // Map each CartItem → CartItemDto
        dto.setItems(
                cart.getItems()
                        .stream()
                        .map(CartService::toItemDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    private static CartItemDto toItemDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setSupplementId(item.getSupplementId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        // Compute total price per line
        dto.setTotalPrice(
                item.getUnitPrice() == null
                        ? BigDecimal.ZERO
                        : item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
        return dto;
    }
}
