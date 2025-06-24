package com.example.wellnesstracker.controller;


import com.example.wellnesstracker.dto.auth.LoginDto;
import com.example.wellnesstracker.dto.auth.LoginResponseDto;
import com.example.wellnesstracker.dto.auth.RegisterDto;
import com.example.wellnesstracker.dto.auth.RegisterResponseDto;
import com.example.wellnesstracker.dto.token.TokenRefreshDto;
import com.example.wellnesstracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterDto requestBody){
        RegisterResponseDto dto = authService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto requestBody){
        LoginResponseDto dto = authService.login(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(){
        // This endpoint requires ADMIN role
        return ResponseEntity.ok("Admin only endpoint");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenRefreshDto request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }

}