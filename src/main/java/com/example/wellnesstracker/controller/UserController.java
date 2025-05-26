package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.dto.UserDto;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.UserRepository;
import com.example.wellnesstracker.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(Principal principal) {
        String username = principal.getName();
        UserDto userDto = userService.getUserByUserName(username);

        return ResponseEntity.ok(userDto);
    }
}
