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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService{

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));
    }


    @Transactional
    public CartItem addItemToCart(String userId, String supplementId, int quantity, BigDecimal unitPrice) {
        Cart cart = getCartByUserId(userId);

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getSupplementId().equals(supplementId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update existing item
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            updateCartTotal(cart);
            cartRepository.save(cart);
            return item;
        } else {
            // Add new item
            CartItem newItem = new CartItem();
            newItem.setSupplementId(supplementId);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(unitPrice);
            newItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
            newItem.setCart(cart);

            cart.getItems().add(newItem);
            updateCartTotal(cart);
            cartRepository.save(cart);
            return newItem;
        }
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

    private Cart createNewCart(String userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartDto updateCart(UpdateCartDto cartDto) {
        return null;
    }

    @Override
    public void deleteCart(String userId) {

    }

    @Override
    @Transactional
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

    private void updateCartTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}
