package com.example.wellnesstracker.service;

import com.example.wellnesstracker.model.WellnessRecord;
import com.example.wellnesstracker.repository.WellnessRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WellnessRecordService {
    @Autowired
    private WellnessRecordRepository wellnessRecordRepository;

    public WellnessRecord createWellnessRecord(WellnessRecord record) {
        return wellnessRecordRepository.save(record);
    }

    public List<WellnessRecord> getUserWellnessRecords(String userId) {
        return wellnessRecordRepository.findByUserId(userId);
    }

    public List<WellnessRecord> getWellnessRecordsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return wellnessRecordRepository.findByRecordDateBetween(startDate, endDate);
    }

    public List<WellnessRecord> getAllWellnessRecords() {
        return wellnessRecordRepository.findAll();
    }
}