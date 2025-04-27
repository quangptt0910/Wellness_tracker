package com.example.wellnesstracker.repository;


import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.dto.SupplementDto;
import com.example.wellnesstracker.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {
    Optional<Supplement> findById(long id);
    List<Supplement> findByCategory(Category category);
    List<Supplement> findByNameContainingIgnoreCase(String name);
}