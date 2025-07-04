package com.example.wellnesstracker.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String refreshToken;
    private long expiresIn;
}