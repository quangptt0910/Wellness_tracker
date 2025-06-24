package com.example.wellnesstracker.dto.token;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokenRefreshResponseDto {
    private String token;
    private String refreshToken;
    private Long expiresIn; // Token expiration time in milliseconds
}