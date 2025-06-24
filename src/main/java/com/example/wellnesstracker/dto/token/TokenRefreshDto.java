package com.example.wellnesstracker.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokenRefreshDto {
    private String refreshToken;
}