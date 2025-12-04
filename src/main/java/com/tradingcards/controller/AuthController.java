package com.tradingcards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tradingcards.model.User;
import com.tradingcards.repository.UserRepository;
import com.tradingcards.util.PasswordHasher;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    // Show login page
    @GetMapping("/login")
    public String showLoginPage(Model model,
                               @RequestParam(required = false) String error,
                               @RequestParam(required = false) String logout) {
        
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("logout", "You have been logged out successfully.");
        }
        
        return "login";
    }
    
    // Process login
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, 
                           @RequestParam String password,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found!");
            return "redirect:/login";
        }
        
        // Validate password
        if (!PasswordHasher.validatePassword(password, user.getPasswordHash())) {
            redirectAttributes.addFlashAttribute("error", "Invalid password!");
            return "redirect:/login";
        }
        
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("username", user.getUsername());
        
        return "redirect:/";
    }
    
    // Show register page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    // Process registration
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        
        if (userRepository.findByUsername(username).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Username already exists!");
            return "redirect:/register";
        }
        
        if (userRepository.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email already registered!");
            return "redirect:/register";
        }
        
        // Hash password
        String passwordHash = PasswordHasher.hashPassword(password);
        
        User newUser = new User(username, email, passwordHash);
        userRepository.save(newUser);
        
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }
    
    // Logout
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}