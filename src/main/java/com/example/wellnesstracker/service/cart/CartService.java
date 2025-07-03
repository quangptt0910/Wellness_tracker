package com.example.wellnesstracker.service.cart;

import com.example.wellnesstracker.dto.cart.CartDto;
import com.example.wellnesstracker.dto.cart.CreateCartDto;
import com.example.wellnesstracker.dto.cart.UpdateCartDto;
import com.example.wellnesstracker.dto.cartItem.CartItemDto;
import com.example.wellnesstracker.dto.cartItem.CreateCartItemDto;
import com.example.wellnesstracker.model.Cart;
import com.example.wellnesstracker.model.CartItem;
import com.example.wellnesstracker.model.Supplement;
import com.example.wellnesstracker.repository.CartRepository;
import com.example.wellnesstracker.repository.SupplementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final SupplementRepository supplementRepository;

    public Optional<CartDto> getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId)
                .map(this::convertToDto);
    }


    public CartDto createCart(CreateCartDto createCartDto) {
        Cart cart = new Cart();
        cart.setUserId(createCartDto.getUserId());
        cart.setItems(new ArrayList<>());
        cart.setTotalAmount(BigDecimal.ZERO);

        if (createCartDto.getItems() != null && !createCartDto.getItems().isEmpty()) {
            List<CartItem> cartItems = createCartDto.getItems().stream()
                    .map(itemDto -> createCartItem(itemDto, cart))
                    .collect(Collectors.toList());
            cart.setItems(cartItems);
            updateCartTotal(cart);
        }


        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    @Transactional
    public CartDto addItemToCart(String userId, CreateCartItemDto createCartItemDto) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setItems(new ArrayList<>());
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    return cartRepository.save(newCart);
                });

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getSupplementId().equals(createCartItemDto.getSupplementId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity of existing item
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + createCartItemDto.getQuantity());
            item.setTotalPrice();
        } else {
            // Add new item
            CartItem newItem = createCartItem(createCartItemDto, cart);
            cart.getItems().add(newItem);
        }

        updateCartTotal(cart);
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }


    /* Update cart Item */
    @Transactional
    public CartDto updateCart(String userId, UpdateCartDto updateCartDto) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        // Clear existing items and add new ones
        cart.getItems().clear();

        if (updateCartDto.getItems() != null) {
            List<CartItem> cartItems = updateCartDto.getItems().stream()
                    .map(itemDto -> createCartItem(itemDto, cart))
                    .collect(Collectors.toList());
            cart.setItems(cartItems);
        }

        updateCartTotal(cart);
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    public CartDto removeItemFromCart(String userId, String supplementId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        cart.getItems().removeIf(item -> item.getSupplementId().equals(supplementId));
        updateCartTotal(cart);

        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    public CartDto updateItemQuantity(String userId, String supplementId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getSupplementId().equals(supplementId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setTotalPrice();
        }

        updateCartTotal(cart);
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }


    public void deleteCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }


    public int getCartItemCount(String userId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> cart.getItems().stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum())
                .orElse(0);
    }


    public BigDecimal getCartTotal(String userId) {
        return cartRepository.findByUserId(userId)
                .map(Cart::getTotalAmount)
                .orElse(BigDecimal.ZERO);
    }


    // Helper methods

    private CartItem createCartItem(CreateCartItemDto itemDto, Cart cart) {
        // Validate supplement exists
        Supplement supplement = supplementRepository.findById(itemDto.getSupplementId())
                .orElseThrow(() -> new RuntimeException("Supplement not found: " + itemDto.getSupplementId()));

        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID().toString());
        cartItem.setSupplementId(itemDto.getSupplementId());
        cartItem.setQuantity(itemDto.getQuantity());
        cartItem.setUnitPrice(itemDto.getUnitPrice() != null ? itemDto.getUnitPrice() : supplement.getPrice());
        cartItem.setTotalPrice();
        cartItem.setCart(cart);

        return cartItem;
    }

    private void updateCartTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }

    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUserId());
        cartDto.setTotalAmount(cart.getTotalAmount());

        List<CartItemDto> itemDtos = cart.getItems().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList());
        cartDto.setItems(itemDtos);

        return cartDto;
    }

    private CartItemDto convertItemToDto(CartItem cartItem) {
        CartItemDto itemDto = new CartItemDto();
        itemDto.setId(cartItem.getId());
        itemDto.setSupplementId(cartItem.getSupplementId());
        itemDto.setQuantity(cartItem.getQuantity());
        itemDto.setUnitPrice(cartItem.getUnitPrice());
        itemDto.setTotalPrice(cartItem.getTotalPrice());
        return itemDto;
    }
}
