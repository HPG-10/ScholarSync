package com.scholarsync.service;

import com.scholarsync.model.Scholarship;
import com.scholarsync.model.Student;
import com.scholarsync.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScholarshipService {

    @Autowired
    private ScholarshipRepository scholarshipRepository;

    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }

    public Scholarship getById(Long id) {
        return scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
    }

    public List<Scholarship> getMatchingScholarships(Student student) {
        return scholarshipRepository.findMatchingScholarships(
                student.getCgpa(),
                student.getFaculty()
        );
    }

    public List<Scholarship> searchScholarships(String keyword) {
        return scholarshipRepository.searchByKeyword(keyword);
    }

    public Scholarship save(Scholarship scholarship) {
        return scholarshipRepository.save(scholarship);
    }

    public void delete(Long id) {
        scholarshipRepository.deleteById(id);
    }

    public List<Scholarship> getByAdmin(Long adminId) {
        return scholarshipRepository.findByAdminAdminId(adminId);
    }
}
