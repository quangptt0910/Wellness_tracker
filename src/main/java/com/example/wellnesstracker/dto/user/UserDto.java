package com.example.wellnesstracker.dto.user;

import com.example.wellnesstracker.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;

    @NotEmpty
    private String name;
    private String surname;

    @NotEmpty
    @Email
    private String email;

    private Gender gender;
    private Double height;
    private Double weight;
}