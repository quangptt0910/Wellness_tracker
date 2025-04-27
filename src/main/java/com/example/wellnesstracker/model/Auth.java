package com.example.wellnesstracker.model;

import com.example.wellnesstracker.common.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth", schema = "pharmacy")
public class Auth{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    @Basic
    private String username;

    @Column(name = "password", nullable = false)
    @Basic
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}