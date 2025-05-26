package com.example.wellnesstracker.dto;

import com.example.wellnesstracker.model.Auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;
    
    public UserDto() {}

}