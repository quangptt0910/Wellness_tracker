package com.example.wellnesstracker.service.user.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFound {
    public static ResponseStatusException createWithId(long id){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found with id %d", id));
    }

    public static ResponseStatusException createWithUsername(String username){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not found with username %s", username));
    }
}