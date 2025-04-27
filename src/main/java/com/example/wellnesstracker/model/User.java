package com.example.wellnesstracker.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "users", schema = "pharmacy")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Basic
    private String name;


    @Column(name = "email")
    @Basic
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Auth auth;

}