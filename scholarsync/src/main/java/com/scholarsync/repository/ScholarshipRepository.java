package com.scholarsync.repository;

import com.scholarsync.model.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {

    // Find scholarships that match a student's CGPA and faculty
    @Query("SELECT s FROM Scholarship s WHERE " +
           "(s.minCgpa IS NULL OR s.minCgpa <= :cgpa) AND " +
           "(s.faculty IS NULL OR s.faculty = :faculty OR s.faculty = '')")
    List<Scholarship> findMatchingScholarships(@Param("cgpa") Double cgpa,
                                               @Param("faculty") String faculty);

    // Search by keyword in name or provider
    @Query("SELECT s FROM Scholarship s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.provider) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Scholarship> searchByKeyword(@Param("keyword") String keyword);

    List<Scholarship> findByAdminAdminId(Long adminId);
}
