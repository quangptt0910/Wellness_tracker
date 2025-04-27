package com.example.wellnesstracker.service;


import com.example.wellnesstracker.dto.LoginDto;
import com.example.wellnesstracker.dto.LoginResponseDto;
import com.example.wellnesstracker.dto.RegisterDto;
import com.example.wellnesstracker.dto.RegisterResponseDto;
import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.repository.UserRepository;
import com.example.wellnesstracker.service.error.UserAlreadyExistsException;
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

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
        auth.setUser(createdUser);

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