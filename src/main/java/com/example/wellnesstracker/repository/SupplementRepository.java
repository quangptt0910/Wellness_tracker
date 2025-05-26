package com.example.wellnesstracker.repository;


import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.model.Supplement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplementRepository extends MongoRepository<Supplement, String> {
    Optional<Supplement> findById(long id);
    List<Supplement> findByCategory(Category category);
    List<Supplement> findByNameContainingIgnoreCase(String name);
}