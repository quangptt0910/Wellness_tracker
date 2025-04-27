package com.example.wellnesstracker.dto;

public class LoginResponseDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponseDto(String token){
        this.token = token;
    }
}