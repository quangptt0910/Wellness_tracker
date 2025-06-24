package com.example.wellnesstracker.dto.user;

import com.example.wellnesstracker.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {

    private String name;
    private String surname;

    @Email(message = "Email should be valid")
    private String email;

    private Gender gender;
    private Double height;
    private Double weight;
}