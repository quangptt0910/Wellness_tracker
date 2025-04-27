package com.example.wellnesstracker.controller;

import com.example.wellnesstracker.model.Supplement;
import com.example.wellnesstracker.model.WellnessRecord;
import com.example.wellnesstracker.service.WellnessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wellness")
public class WellnessRecordController {
    @Autowired
    private WellnessRecordService wellnessRecordService;

    @PostMapping("/record")
    public WellnessRecord createWellnessRecord(@RequestBody WellnessRecord record) {
        return wellnessRecordService.createWellnessRecord(record);
    }

    @GetMapping
    public List<WellnessRecord> getAllWellnessRecords() {
        return wellnessRecordService.getAllWellnessRecords();
    }

    @GetMapping("/user/{userId}")
    public List<WellnessRecord> getUserWellnessRecords(@PathVariable String userId) {
        return wellnessRecordService.getUserWellnessRecords(userId);
    }

    @GetMapping("/records")
    public List<WellnessRecord> getWellnessRecordsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return wellnessRecordService.getWellnessRecordsBetweenDates(startDate, endDate);
    }

}