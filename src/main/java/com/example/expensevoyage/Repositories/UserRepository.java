package com.example.expensevoyage.Repositories;

import com.example.expensevoyage.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}