package com.example.wellnesstracker.model;


import com.example.wellnesstracker.common.Category;
import com.example.wellnesstracker.common.DosageUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "supplements", schema = "pharmacy")
public class Supplement {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "manufacturer")
    private String manufacturer;

    @ElementCollection
    @Column(name = "benefits")
    private List<String> benefits = new ArrayList<>();

    @Column(name = "dosageAmount")
    private Integer dosageAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosageUnit")
    private DosageUnit dosageUnit;

    @Column(name = "price")
    private Double price;

    // Constructors
    public Supplement() {}

    public Supplement(String name, Category category, String manufacturer) {
        this.name = name;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    // Methods to add benefits and ingredients
    public void addBenefit(String benefit) {
        if (!benefits.contains(benefit)) {
            benefits.add(benefit);
        }
    }

}
