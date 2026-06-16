package com.scholarsync.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;

    @Column(name = "date_applied")
    private LocalDate dateApplied = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED
    }

    public Application() {}

    public Long getApplId() { return applId; }
    public void setApplId(Long applId) { this.applId = applId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Scholarship getScholarship() { return scholarship; }
    public void setScholarship(Scholarship scholarship) { this.scholarship = scholarship; }

    public LocalDate getDateApplied() { return dateApplied; }
    public void setDateApplied(LocalDate dateApplied) { this.dateApplied = dateApplied; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}
