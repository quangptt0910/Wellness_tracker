package com.example.wellnesstracker.dto;

import com.example.wellnesstracker.model.Auth;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {

    private long id;

    @NotEmpty
    private String name;


    @NotEmpty
    @Email
    private String email;

    private Auth auth;
    
    public UserDto() {}

    public UserDto(long id, String name, String email, Auth auth) {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}