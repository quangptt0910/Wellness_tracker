package com.example.wellnesstracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {

    @Size(min = 1, message = "Name must not be empty")
    private String name;

    @Email(message = "Email should be valid")
    private String email;
}