package com.scholarsync.repository;

import com.scholarsync.model.Application;
import com.scholarsync.model.Student;
import com.scholarsync.model.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(Student student);
    Optional<Application> findByStudentAndScholarship(Student student, Scholarship scholarship);
    List<Application> findByScholarshipScholarshipId(Long scholarshipId);
}
