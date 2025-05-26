package com.example.wellnesstracker.repository;

import com.example.wellnesstracker.model.WellnessRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WellnessRecordRepository extends MongoRepository<WellnessRecord, String> {
    List<WellnessRecord> findByUserId(String userId);
    List<WellnessRecord> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);

}