package com.example.expensevoyage.Repositories;

import com.example.expensevoyage.Models.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> authenticateUser(String email, String password);
    Optional<User> getUserByEmail(String email);
    User updateUser(User user);
    void deleteUser(User user);
}
