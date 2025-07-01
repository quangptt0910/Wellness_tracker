package com.example.wellnesstracker.dto.supplement;

import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplementDto {

    private String id;

    @NotNull(message = "Name cannot be null")
    private String name;
    private Category category;
    private String manufacturer;
    private List<String> benefits;
    private Integer dosageAmount;
    private DosageUnit dosageUnit;
    private BigDecimal price;

    private String imageName;      // Raw filename stored in DB

    // Image utility methods in DTO
    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageName != null ? "/api/images/supplements/" + imageName : null;
    }

    @JsonProperty("hasImage")
    public boolean hasImage() {
        return imageName != null && !imageName.isEmpty();
    }
}
