package com.scholarsync.service;

import com.scholarsync.model.Admin;
import com.scholarsync.model.Student;
import com.scholarsync.repository.AdminRepository;
import com.scholarsync.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check admin first (admin logs in with username)
        var adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // Check student (student logs in with email)
        var studentOpt = studentRepository.findByEmail(username);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    student.getEmail(),
                    student.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
