package com.example.wellnesstracker.dto.cart;

import com.example.wellnesstracker.dto.cartItem.CreateCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartDto {
    private List<CreateCartItemDto> items;
}
