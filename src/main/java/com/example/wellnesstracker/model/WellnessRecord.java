package com.example.wellnesstracker.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection = "wellnessRecord")
public class WellnessRecord {

    @Id
    private Long id;

    private String userId;
    private LocalDate recordDate;
    private List<Long> supplementIds = new ArrayList<>();
    private List<String> dailySymptoms = new ArrayList<>();
    private double weight;

    private int sleepHours;

    private int stressLevel; // 1-10 scale


    private int energyLevel; // 1-10 scale


    private List<String> notes = new ArrayList<>();

    // Constructors
    public WellnessRecord() {}

    public WellnessRecord(String userId, LocalDate recordDate) {
        this.userId = userId;
        this.recordDate = recordDate;
    }

    // Method to add supplements taken
    public void addSupplement(Long supplementId) {
        if (!supplementIds.contains(supplementId)) {
            supplementIds.add(supplementId);
        }
    }

    public void addSymptom(String symptom) {
        if (!dailySymptoms.contains(symptom)) {
            dailySymptoms.add(symptom);
        }
    }

    public void addNote(String note) {
        notes.add(note);
    }

}
