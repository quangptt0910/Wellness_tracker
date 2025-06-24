package com.example.wellnesstracker.service;

import com.example.wellnesstracker.model.RefreshToken;
import com.example.wellnesstracker.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor()
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.refresh-token.expiration-time}")
    private long refreshTokenExpiration;

    public RefreshToken createToken(String userId) {
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setCreatedAt(Instant.now());
        token.setExpiresAt(Instant.now().plus(refreshTokenExpiration, ChronoUnit.DAYS)); // 30-day expiry

        return refreshTokenRepository.save(token);
    }

    public void invalidateToken(String tokenId) {
        refreshTokenRepository.deleteById(tokenId);
    }

    public void invalidateAllUserTokens(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public boolean validateToken(String tokenId) {
        return refreshTokenRepository.findById(tokenId)
                .map(token -> token.getExpiresAt().isAfter(Instant.now()))
                .orElse(false);
    }

    public Optional<RefreshToken> findById(String tokenId) {
        return refreshTokenRepository.findById(tokenId)
                .filter(token -> token.getExpiresAt().isAfter(Instant.now()));
    }
}