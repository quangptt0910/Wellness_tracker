package com.example.wellnesstracker.dto.supplement;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSupplementDto {

    @NotNull
    private String name;

    @NotNull
    private Category category;

    private String manufacturer;

    private List<String> benefits;

    @NotNull
    private Integer dosageAmount;

    @NotNull
    private DosageUnit dosageUnit;

    @NotNull
    private BigDecimal price;
}
