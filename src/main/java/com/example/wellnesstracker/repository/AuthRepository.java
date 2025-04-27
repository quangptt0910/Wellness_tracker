package com.example.wellnesstracker.repository;


import com.example.wellnesstracker.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUsername(String username);
    boolean existsByUsername(String username);
}