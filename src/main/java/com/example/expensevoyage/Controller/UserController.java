package com.example.expensevoyage.Controller;

import com.example.expensevoyage.Models.User;
import com.example.expensevoyage.Repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Display the login page
    @GetMapping("/login")
    public String login() {
        return "login";  // Thymeleaf login page
    }

    // Handle login form submission and authenticate user
    @PostMapping("/login")
    public String authenticate(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        Optional<User> userOptional = userService.authenticateUser(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.setAttribute("loggedInUser", user);  // Store user in session
            return "redirect:/dashboard";  // Redirect to user dashboard
        }
        model.addAttribute("error", "Invalid email or password");
        return "login";  // Redisplay login with error message
    }

    // Display the registration page
    @GetMapping("/register")
    public String register() {
        return "register";  // Thymeleaf registration page
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        Optional<User> existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Email already in use");
            return "register";
        }
        userService.registerUser(user);  // Register the new user
        return "redirect:/login";  // Redirect to login page after registration
    }

    // Display the user dashboard
    @GetMapping("/dashboard")
    public String dashboard(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";  // If not logged in, redirect to login
        }
        model.addAttribute("user", loggedInUser);  // Pass user to dashboard
        return "dashboard";  // Thymeleaf dashboard page
    }

    // Handle logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate the session
        return "redirect:/login";  // Redirect to login page
    }

    // Display the user profile
    @GetMapping("/profile")
    public String profile(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";  // If not logged in, redirect to login
        }
        model.addAttribute("user", loggedInUser);  // Pass user to profile page
        return "profile";  // Thymeleaf profile page
    }

    // Handle profile updates
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";  // If not logged in, redirect to login
        }

        loggedInUser.setName(user.getName());
        loggedInUser.setEmail(user.getEmail());
        loggedInUser.setPassword(user.getPassword());
        userService.updateUser(loggedInUser);  // Update user info in the database

        session.setAttribute("loggedInUser", loggedInUser);  // Update session info
        model.addAttribute("user", loggedInUser);
        model.addAttribute("success", "Profile updated successfully");
        return "profile";  // Redisplay profile page with success message
    }

    // Handle user deletion
    @PostMapping("/delete")
    public String deleteUser(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            userService.deleteUser(loggedInUser);  // Delete user from the database
            session.invalidate();  // Invalidate session
        }
        return "redirect:/register";  // Redirect to registration page after deletion
    }
}
