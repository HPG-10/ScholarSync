package com.scholarsync.service;

import com.scholarsync.model.Application;
import com.scholarsync.model.Application.ApplicationStatus;
import com.scholarsync.model.Scholarship;
import com.scholarsync.model.Student;
import com.scholarsync.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application apply(Student student, Scholarship scholarship) {
        // Check if already applied
        var existing = applicationRepository.findByStudentAndScholarship(student, scholarship);
        if (existing.isPresent()) {
            throw new RuntimeException("You have already applied for this scholarship.");
        }
        Application app = new Application();
        app.setStudent(student);
        app.setScholarship(scholarship);
        app.setDateApplied(LocalDate.now());
        app.setStatus(ApplicationStatus.PENDING);
        return applicationRepository.save(app);
    }

    public List<Application> getStudentApplications(Student student) {
        return applicationRepository.findByStudent(student);
    }

    public List<Application> getApplicationsForScholarship(Long scholarshipId) {
        return applicationRepository.findByScholarshipScholarshipId(scholarshipId);
    }

    public Application updateStatus(Long applId, ApplicationStatus status) {
        Application app = applicationRepository.findById(applId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        return applicationRepository.save(app);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
