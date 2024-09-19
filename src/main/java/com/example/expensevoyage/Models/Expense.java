package com.example.expensevoyage.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String note;
    private LocalDate date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Add this part
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    // Getters, Setters, Constructors

    public Expense() {}

    public Expense(String category, String note, LocalDate date, double amount, User user, Trip trip) {
        this.category = category;
        this.note = note;
        this.date = date;
        this.amount = amount;
        this.user = user;
        this.trip = trip;
    }
}
