package com.example.wellnesstracker.dto.supplement;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import jakarta.validation.constraints.DecimalMin;
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

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}
