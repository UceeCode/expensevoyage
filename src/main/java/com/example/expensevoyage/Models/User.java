package com.example.expensevoyage.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String preferredCurrency;

    // Getters, Setters, Constructors

    public User() {}

    public User(String email, String password, String name, String preferredCurrency) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.preferredCurrency = preferredCurrency;
    }

}

