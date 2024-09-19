package com.example.expensevoyage.Repositories;

import com.example.expensevoyage.Models.Expense;
import com.example.expensevoyage.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
