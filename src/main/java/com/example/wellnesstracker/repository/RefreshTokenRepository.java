package com.example.wellnesstracker.repository;

import com.example.wellnesstracker.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository  extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findByIdAndExpiresAtAfter(String id, Instant expiresAtAfter);
    void deleteByExpiresAtBefore(Instant now);
    void deleteByUserId(String userId);
}
