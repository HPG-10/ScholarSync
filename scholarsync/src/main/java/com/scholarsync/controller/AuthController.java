package com.scholarsync.controller;

import com.scholarsync.model.Student;
import com.scholarsync.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private StudentService studentService;

    // ---- Login ----
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid username or password.");
        if (logout != null) model.addAttribute("message", "You have been logged out.");
        return "auth/login";
    }

    // ---- Register ----
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("student", new Student());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute Student student,
                                  RedirectAttributes redirectAttributes) {
        try {
            studentService.register(student);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}
