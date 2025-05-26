package com.example.wellnesstracker.dto.supplement;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSupplementDto {

    private String name;

    private String category;

    private String manufacturer;

    private List<String> benefits;

    private Integer dosageAmount;

    private DosageUnit dosageUnit;

    private Double price;
}
