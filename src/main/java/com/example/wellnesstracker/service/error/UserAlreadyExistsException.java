package com.example.wellnesstracker.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String username){
        UserAlreadyExistsException exception = new UserAlreadyExistsException(String.format("User '%s' already exists", username));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}