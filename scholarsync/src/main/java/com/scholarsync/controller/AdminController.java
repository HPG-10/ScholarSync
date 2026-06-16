package com.scholarsync.controller;

import com.scholarsync.model.*;
import com.scholarsync.model.Application.ApplicationStatus;
import com.scholarsync.repository.AdminRepository;
import com.scholarsync.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private ScholarshipService scholarshipService;
    @Autowired private ApplicationService applicationService;
    @Autowired private AdminRepository adminRepository;

    private Admin getCurrentAdmin(UserDetails userDetails) {
        return adminRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    // ---- List all scholarships (Admin) ----
    @GetMapping("/scholarships")
    public String adminScholarships(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Scholarship> scholarships = scholarshipService.getAllScholarships();
        model.addAttribute("scholarships", scholarships);
        return "admin/scholarship-list";
    }

    // ---- Add scholarship form ----
    @GetMapping("/scholarships/add")
    public String addForm(Model model) {
        model.addAttribute("scholarship", new Scholarship());
        return "admin/scholarship-form";
    }

    // ---- Edit scholarship form ----
    @GetMapping("/scholarships/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("scholarship", scholarshipService.getById(id));
        return "admin/scholarship-form";
    }

    // ---- Save (add or update) scholarship ----
    @PostMapping("/scholarships/save")
    public String saveScholarship(@ModelAttribute Scholarship scholarship,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   RedirectAttributes redirectAttributes) {
        Admin admin = getCurrentAdmin(userDetails);
        scholarship.setAdmin(admin);
        scholarshipService.save(scholarship);
        redirectAttributes.addFlashAttribute("message", "Scholarship saved successfully!");
        return "redirect:/admin/scholarships";
    }

    // ---- Delete scholarship ----
    @PostMapping("/scholarships/delete/{id}")
    public String deleteScholarship(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        scholarshipService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Scholarship deleted.");
        return "redirect:/admin/scholarships";
    }

    // ---- Manage all applications ----
    @GetMapping("/applications")
    public String manageApplications(Model model) {
        model.addAttribute("applications", applicationService.getAllApplications());
        return "admin/applications";
    }

    // ---- Update application status ----
    @PostMapping("/applications/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam ApplicationStatus status,
                               RedirectAttributes redirectAttributes) {
        applicationService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("message", "Status updated to " + status);
        return "redirect:/admin/applications";
    }
}
