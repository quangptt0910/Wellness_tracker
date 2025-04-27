package com.example.wellnesstracker.dto;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplementDto {

    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;
    private Category category;         // Represented as String for simplicity
    private String manufacturer;
    private List<String> benefits;
    private Integer dosageAmount;
    private DosageUnit dosageUnit;         // Represented as String
    private Double price;

}
