package com.example.wellnesstracker.service;


import com.example.wellnesstracker.common.UserRole;
import com.example.wellnesstracker.dto.auth.LoginDto;
import com.example.wellnesstracker.dto.auth.LoginResponseDto;
import com.example.wellnesstracker.dto.auth.RegisterDto;
import com.example.wellnesstracker.dto.auth.RegisterResponseDto;
import com.example.wellnesstracker.dto.token.TokenRefreshDto;
import com.example.wellnesstracker.dto.token.TokenRefreshResponseDto;
import com.example.wellnesstracker.exception.InvalidCredentialsException;
import com.example.wellnesstracker.exception.TokenExpiredException;
import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.model.RefreshToken;
import com.example.wellnesstracker.model.User;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.repository.RefreshTokenRepository;
import com.example.wellnesstracker.repository.UserRepository;
import com.example.wellnesstracker.service.user.error.UserAlreadyExistsException;
import com.example.wellnesstracker.service.user.error.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public RegisterResponseDto register(RegisterDto dto){
        Optional<Auth> existingAuth = authRepository.findByUsername(dto.getUsername());

        if (existingAuth.isPresent()){
            throw UserAlreadyExistsException.create(dto.getUsername());
        }

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        User createdUser = userRepository.save(user);

        Auth auth = new Auth();
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setUsername(dto.getUsername());
        auth.setRole(dto.getRole() != null ? dto.getRole() : UserRole.ROLE_USER);
        auth.setEnabled(true);
        auth.setUserId(createdUser.getId());

        Auth createdAuth = authRepository.save(auth);
        String token = jwtService.generateToken(createdAuth);
        RefreshToken refreshToken = refreshTokenService.createToken(token);
        return new RegisterResponseDto(
                createdAuth.getUsername(),
                createdAuth.getRole(),
                token,
                refreshToken.getId(),
                jwtService.getExpirationTime(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
    }

    public LoginResponseDto login(LoginDto dto){
        final var authToken = UsernamePasswordAuthenticationToken.unauthenticated(dto.getUsername(), dto.getPassword());
        final var authentication = authenticationManager.authenticate(authToken);

        Auth auth = authRepository.findByUsername(dto.getUsername()).orElseThrow(InvalidCredentialsException::create);

        if(!passwordEncoder.matches(dto.getPassword(), auth.getPassword())){
            throw InvalidCredentialsException.create(dto.getUsername());
        }

        String token = jwtService.generateToken(auth);
        RefreshToken refreshToken = createAndSaveRefreshToken(auth.getUserId());

        return new LoginResponseDto(token, refreshToken.getId(), jwtService.getExpirationTime());
    }

    public TokenRefreshResponseDto refreshToken(TokenRefreshDto request) {
        String refreshTokenId = request.getRefreshToken();

        // Validate refresh token
        if (!refreshTokenService.validateToken(refreshTokenId)) {
            throw new TokenExpiredException("Refresh token is expired or invalid");
        }

        // Get refresh token and find associated auth
        RefreshToken refreshToken = refreshTokenService.findById(refreshTokenId)
                .orElseThrow(() -> new TokenExpiredException("Refresh token not found"));

        Auth auth = authRepository.findByUserId(refreshToken.getUserId())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        // Generate new access token
        String newAccessToken = jwtService.generateToken(auth);

        // Optionally generate new refresh token (rotate refresh tokens)
        RefreshToken newRefreshToken = refreshTokenService.createToken(auth.getUserId());

        // Invalidate old refresh token
        refreshTokenService.invalidateToken(refreshTokenId);

        return new TokenRefreshResponseDto(
                newAccessToken,
                newRefreshToken.getId(),
                jwtService.getExpirationTime()
        );
    }

    public void logout(String refreshTokenId) {
        refreshTokenService.invalidateToken(refreshTokenId);
    }

    private RefreshToken createAndSaveRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS)); // Example: 30-day expiration

        return refreshTokenRepository.save(refreshToken);
    }
}