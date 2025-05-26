package com.example.wellnesstracker.model;

import com.example.wellnesstracker.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "auth")
public class Auth{

    @MongoId
    private String id;

    private String username;
    private String password;
    private UserRole role;
    private boolean enabled = true;

    private String userId;

}