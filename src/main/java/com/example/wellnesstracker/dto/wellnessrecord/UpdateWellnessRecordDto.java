package com.example.wellnesstracker.dto.wellnessrecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateWellnessRecordDto {

    private LocalDate recordDate;

    private List<Long> supplementIds;

    private List<String> dailySymptoms;

    private Double weight;

    private Integer sleepHours;

    private Integer stressLevel;

    private Integer energyLevel;

    private List<String> notes;
}
