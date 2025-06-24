package com.example.wellnesstracker.dto.cart;

import com.example.wellnesstracker.dto.cartItem.CreateCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateCartDto {
    private String userId;
    private List<CreateCartItemDto> items;

    // getters and setters
}