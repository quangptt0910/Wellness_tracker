package com.example.wellnesstracker.dto;

import java.time.LocalDate;
import java.util.List;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public List<Long> getSupplementIds() {
        return supplementIds;
    }

    public void setSupplementIds(List<Long> supplementIds) {
        this.supplementIds = supplementIds;
    }

    public List<String> getDailySymptoms() {
        return dailySymptoms;
    }

    public void setDailySymptoms(List<String> dailySymptoms) {
        this.dailySymptoms = dailySymptoms;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public int getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}
