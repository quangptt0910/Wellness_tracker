package com.example.wellnesstracker.dto.supplement;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSupplementDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Category is required")
    private Category category;

    private String manufacturer;

    private List<String> benefits;

    private Integer dosageAmount;

    private DosageUnit dosageUnit;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}
