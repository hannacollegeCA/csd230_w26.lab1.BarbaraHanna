package csd230.lab1.controllers;

import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    private UserEntityRepository userRepository; // Repository used to save new users

    @Autowired
    private PasswordEncoder passwordEncoder; // Encrypts passwords before saving

    // Show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity()); // Empty user object for the form
        return "register"; // Load register.html
    }

    // Handle form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserEntity user) {

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Default role for new users
        user.setRole("USER");

        // Save the new user in the database
        userRepository.save(user);

        // Redirect to login page after successful registration
        return "redirect:/login";
    }

}
