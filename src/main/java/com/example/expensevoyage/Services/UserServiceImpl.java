package com.example.expensevoyage.Services;

import com.example.expensevoyage.Models.User;
import com.example.expensevoyage.Repositories.UserRepository;
import com.example.expensevoyage.Repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register a new user
    @Override
    public User registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);  // Save the new user in the database
    }

    // Authenticate a user by email and password
    @Override
    public Optional<User> authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user);  // Return the authenticated user
        }
        return Optional.empty();  // Return empty if authentication fails
    }

    // Retrieve a user by their email
    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    // Update user details (e.g., profile information)
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);  // Save updated user information
    }

    // Delete a user from the system
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);  // Remove user from the database
    }
}
