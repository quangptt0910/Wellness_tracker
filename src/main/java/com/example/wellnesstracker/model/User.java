package com.example.wellnesstracker.model;


import com.example.wellnesstracker.common.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String name;
    private String surname;
    private String email;

    private Gender gender;
    private Double height;
    private Double weight;
}