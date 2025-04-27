package com.example.wellnesstracker.repository;

import com.example.wellnesstracker.model.WellnessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WellnessRecordRepository extends JpaRepository<WellnessRecord, Long> {
    List<WellnessRecord> findByUserId(String userId);
    List<WellnessRecord> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);

}