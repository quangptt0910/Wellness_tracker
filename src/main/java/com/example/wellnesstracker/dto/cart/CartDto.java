package com.example.wellnesstracker.dto.cart;

import com.example.wellnesstracker.dto.cartItem.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String id;
    private String userId;
    private BigDecimal totalAmount;
    private List<CartItemDto> items;
}