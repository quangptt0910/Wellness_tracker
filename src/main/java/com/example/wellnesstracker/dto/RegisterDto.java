package com.example.wellnesstracker.dto;

/*
@NotNull: a constrained CharSequence, Collection, Map, or Array is valid as long as it’s not null, but it can be empty.
@NotEmpty: a constrained CharSequence, Collection, Map, or Array is valid as long as it’s not null, and its size/length is greater than zero.
@NotBlank: a constrained String is valid as long as it’s not null, and the trimmed length is greater than zero.
 */
import com.example.wellnesstracker.common.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull
    private UserRole role;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    public RegisterDto(String password, String username, UserRole role, String email) {
        this.password = password;
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}