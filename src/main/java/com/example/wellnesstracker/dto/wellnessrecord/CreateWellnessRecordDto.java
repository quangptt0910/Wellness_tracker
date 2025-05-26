package com.example.wellnesstracker.dto.wellnessrecord;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateWellnessRecordDto {

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Record date is required")
    private LocalDate recordDate;

    private List<Long> supplementIds;

    private List<String> dailySymptoms;

    private Double weight;

    private Integer sleepHours;

    private Integer stressLevel;

    private Integer energyLevel;

    private List<String> notes;
}
