package com.example.wellnesstracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class WellnessRecordDto {
    private Long id;
    private String userId;
    private LocalDate recordDate;
    private List<Long> supplementIds;
    private List<String> dailySymptoms;
    private double weight;
    private int sleepHours;
    private int stressLevel;
    private int energyLevel;
    private List<String> notes;

    // Constructors, getters, and setters
    public WellnessRecordDto() {}

    public WellnessRecordDto(Long id, String userId, LocalDate recordDate, List<Long> supplementIds,
                             List<String> dailySymptoms, double weight, int sleepHours,
                             int stressLevel, int energyLevel, List<String> notes) {
        this.id = id;
        this.userId = userId;
        this.recordDate = recordDate;
        this.supplementIds = supplementIds;
        this.dailySymptoms = dailySymptoms;
        this.weight = weight;
        this.sleepHours = sleepHours;
        this.stressLevel = stressLevel;
        this.energyLevel = energyLevel;
        this.notes = notes;
    }

}
