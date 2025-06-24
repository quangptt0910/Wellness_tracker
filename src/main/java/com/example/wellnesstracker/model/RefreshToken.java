package com.example.wellnesstracker.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "refresh_tokens")
public class RefreshToken {

    @MongoId
    private String id;

    private String userId;

    private Instant createdAt;
    private Instant expiresAt;
}
