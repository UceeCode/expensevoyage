package com.example.expensevoyage.Repositories;

import com.example.expensevoyage.Models.Trip;
import com.example.expensevoyage.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(User user);
}
