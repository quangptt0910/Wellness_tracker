package com.example.wellnesstracker.model;


import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "supplement")
public class Supplement {

    @Id
    private String id;

    private String name;

    private Category category;

    private String manufacturer;

    private List<String> benefits = new ArrayList<>();

    private Integer dosageAmount;

    private DosageUnit dosageUnit;

    private BigDecimal price;

    private String imageName; // only filename not full path


    // Methods to add benefits
    public void addBenefit(String benefit) {
        if (!benefits.contains(benefit)) {
            benefits.add(benefit);
        }
    }


}
