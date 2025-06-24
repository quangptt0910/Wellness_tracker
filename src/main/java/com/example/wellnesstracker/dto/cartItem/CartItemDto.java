package com.example.wellnesstracker.dto.cartItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private String supplementId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}