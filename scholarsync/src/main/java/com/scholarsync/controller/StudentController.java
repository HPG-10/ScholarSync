package com.scholarsync.controller;

import com.scholarsync.model.*;
import com.scholarsync.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {

    @Autowired private StudentService studentService;
    @Autowired private ScholarshipService scholarshipService;
    @Autowired private ApplicationService applicationService;

    private Student getCurrentStudent(UserDetails userDetails) {
        return studentService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model,
                            HttpServletRequest request) {
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/scholarships";
        }
        Student student = getCurrentStudent(userDetails);
        List<Scholarship> matched = scholarshipService.getMatchingScholarships(student);
        model.addAttribute("student", student);
        model.addAttribute("scholarships", matched);
        model.addAttribute("currentUri", request.getRequestURI());
        return "student/dashboard";
    }

    @GetMapping("/scholarships")
    public String listScholarships(@RequestParam(required = false) String keyword,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model,
                                   HttpServletRequest request) {
        Student student = getCurrentStudent(userDetails);
        List<Scholarship> scholarships;
        if (keyword != null && !keyword.isBlank()) {
            scholarships = scholarshipService.searchScholarships(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            scholarships = scholarshipService.getAllScholarships();
        }
        model.addAttribute("scholarships", scholarships);
        model.addAttribute("student", student);
        model.addAttribute("currentUri", request.getRequestURI());
        return "student/scholarship-list";
    }

    @GetMapping("/scholarships/{id}")
    public String scholarshipDetail(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model,
                                    HttpServletRequest request) {
        Student student = getCurrentStudent(userDetails);
        Scholarship scholarship = scholarshipService.getById(id);
        boolean alreadyApplied = applicationService.getStudentApplications(student)
                .stream().anyMatch(a -> a.getScholarship().getScholarshipId().equals(id));
        model.addAttribute("scholarship", scholarship);
        model.addAttribute("student", student);
        model.addAttribute("alreadyApplied", alreadyApplied);
        model.addAttribute("currentUri", request.getRequestURI());
        return "student/scholarship-detail";
    }

    @PostMapping("/scholarships/{id}/apply")
    public String applyScholarship(@PathVariable Long id,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   RedirectAttributes redirectAttributes) {
        Student student = getCurrentStudent(userDetails);
        Scholarship scholarship = scholarshipService.getById(id);
        try {
            applicationService.apply(student, scholarship);
            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/scholarships/" + id;
    }

    @GetMapping("/applications")
    public String myApplications(@AuthenticationPrincipal UserDetails userDetails,
                                 Model model,
                                 HttpServletRequest request) {
        Student student = getCurrentStudent(userDetails);
        List<Application> applications = applicationService.getStudentApplications(student);
        model.addAttribute("applications", applications);
        model.addAttribute("student", student);
        model.addAttribute("currentUri", request.getRequestURI());
        return "student/applications";
    }
}