package com.scholarsync.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private Double cgpa;

    @Column(length = 100)
    private String faculty;

    @Column(length = 50)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_level")
    private IncomeLevel incomeLevel = IncomeLevel.LOW;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Application> applications;

    public enum IncomeLevel {
        LOW, MEDIUM, HIGH
    }

    public Student() {}

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public IncomeLevel getIncomeLevel() { return incomeLevel; }
    public void setIncomeLevel(IncomeLevel incomeLevel) { this.incomeLevel = incomeLevel; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}
