package com.example.wellnesstracker.dto.auth;

import com.example.wellnesstracker.common.Gender;
import com.example.wellnesstracker.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class RegisterResponseDto {

    private String username;
    private UserRole role;
    private String token;
    private String refreshToken;
    private Long expiresIn;

    private String name;
    private String surname;
    private String email;
}