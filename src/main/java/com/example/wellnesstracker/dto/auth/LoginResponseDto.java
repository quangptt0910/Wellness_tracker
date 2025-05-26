package com.example.wellnesstracker.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
    private String token;

    public LoginResponseDto(String token){
        this.token = token;
    }
}