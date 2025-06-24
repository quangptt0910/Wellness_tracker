package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.dto.user.UpdateUserDto;
import com.example.wellnesstracker.dto.user.UserDto;
import com.example.wellnesstracker.model.CustomUserDetails;
import com.example.wellnesstracker.service.JwtService;
import com.example.wellnesstracker.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;


    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        String username = principal.getName();
        UserDto userDto = userService.getUserByUserName(username);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateCurrentUser(Principal principal,@Valid @RequestBody UpdateUserDto updateDto) {
        String username = principal.getName();
        UserDto currentUser = userService.getUserByUserName(username);
        UserDto updateUser = userService.updateUser(currentUser.getId(), updateDto);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String userId = userDetails.getUserId();
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
