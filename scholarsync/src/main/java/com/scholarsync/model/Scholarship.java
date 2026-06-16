package com.scholarsync.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "scholarship")
public class Scholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scholarshipId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String provider;

    @Column(name = "min_cgpa")
    private Double minCgpa;

    @Column(length = 100)
    private String faculty;

    private LocalDate deadline;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(columnDefinition = "CLOB")
    private String description;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EligibilityCriteria> eligibilityCriterias;

    @OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL)
    private List<Application> applications;

    public Scholarship() {}

    public Long getScholarshipId() { return scholarshipId; }
    public void setScholarshipId(Long scholarshipId) { this.scholarshipId = scholarshipId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public Double getMinCgpa() { return minCgpa; }
    public void setMinCgpa(Double minCgpa) { this.minCgpa = minCgpa; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }

    public List<EligibilityCriteria> getEligibilityCriterias() { return eligibilityCriterias; }
    public void setEligibilityCriterias(List<EligibilityCriteria> eligibilityCriterias) { this.eligibilityCriterias = eligibilityCriterias; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}
