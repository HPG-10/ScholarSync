package com.scholarsync.model;

import jakarta.persistence.*;

@Entity
@Table(name = "eligibility_criteria")
public class EligibilityCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criteriaId;

    @ManyToOne
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;

    @Column(name = "criteria_type", length = 50)
    private String criteriaType;

    @Column(name = "criteria_value", length = 100)
    private String criteriaValue;

    public EligibilityCriteria() {}

    public Long getCriteriaId() { return criteriaId; }
    public void setCriteriaId(Long criteriaId) { this.criteriaId = criteriaId; }

    public Scholarship getScholarship() { return scholarship; }
    public void setScholarship(Scholarship scholarship) { this.scholarship = scholarship; }

    public String getCriteriaType() { return criteriaType; }
    public void setCriteriaType(String criteriaType) { this.criteriaType = criteriaType; }

    public String getCriteriaValue() { return criteriaValue; }
    public void setCriteriaValue(String criteriaValue) { this.criteriaValue = criteriaValue; }
}
