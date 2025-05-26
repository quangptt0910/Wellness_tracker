package com.example.wellnesstracker.service;


import com.example.wellnesstracker.dto.auth.LoginDto;
import com.example.wellnesstracker.dto.auth.LoginResponseDto;
import com.example.wellnesstracker.dto.auth.RegisterDto;
import com.example.wellnesstracker.dto.auth.RegisterResponseDto;
import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.repository.UserRepository;
import com.example.wellnesstracker.service.user.error.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterDto dto){
        Optional<Auth> existingAuth = authRepository.findByUsername(dto.getUsername());

        if (existingAuth.isPresent()){
            throw UserAlreadyExistsException.create(dto.getUsername());
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        User createdUser = userRepository.save(user);

        Auth auth = new Auth();
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setUsername(dto.getUsername());
        auth.setRole(dto.getRole());
        auth.setUserId(createdUser.getId());

        Auth createdAuth = authRepository.save(auth);

        return new RegisterResponseDto(createdAuth.getUsername(),createdAuth.getRole());
    }

    // check if user is correct
    public LoginResponseDto login(LoginDto dto){
        Auth auth = authRepository.findByUsername(dto.getUsername()).orElseThrow(RuntimeException::new);

        if(!passwordEncoder.matches(dto.getPassword(), auth.getPassword())){
            throw new RuntimeException();
        }

        String token = jwtService.generateToken(auth);

        return new LoginResponseDto(token);
    }
}