package com.example.wellnesstracker.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "wellness", schema = "pharmacy")
public class WellnessRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "recordDate")
    private LocalDate recordDate;

    @ElementCollection
    @Column(name = "supplementIds")
    private List<Long> supplementIds = new ArrayList<>();

    @ElementCollection
    @Column(name = "dailySymptoms")
    private List<String> dailySymptoms = new ArrayList<>();

    @Column(name = "weight")
    private double weight;

    @Column(name = "sleepHours")
    private int sleepHours;

    @Column(name = "stressLevel")
    private int stressLevel; // 1-10 scale

    @Column(name = "energyLevel")
    private int energyLevel; // 1-10 scale

    @ElementCollection
    @Column(name = "notes")
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
