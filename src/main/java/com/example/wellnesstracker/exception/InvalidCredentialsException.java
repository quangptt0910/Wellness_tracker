package com.example.wellnesstracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid username or password");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvalidCredentialsException create() {
        return new InvalidCredentialsException();
    }

    public static InvalidCredentialsException create(String username) {
        return new InvalidCredentialsException("Invalid credentials for user: " + username);
    }
}