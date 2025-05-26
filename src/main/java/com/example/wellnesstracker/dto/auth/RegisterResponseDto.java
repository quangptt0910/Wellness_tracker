package com.example.wellnesstracker.dto.auth;

import com.example.wellnesstracker.common.UserRole;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RegisterResponseDto {

    private String username;

    private UserRole role;

    public RegisterResponseDto(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

}