package com.example.wellnesstracker.repository;


import com.example.wellnesstracker.model.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthRepository extends MongoRepository<Auth, String> {
    Optional<Auth> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<Auth> findByUserId(String userId); // Add this method

}