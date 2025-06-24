package com.example.wellnesstracker.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;
}

