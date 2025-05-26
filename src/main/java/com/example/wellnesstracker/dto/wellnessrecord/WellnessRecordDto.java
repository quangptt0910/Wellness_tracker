package com.example.wellnesstracker.dto.wellnessrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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


}
