package com.scholarsync;

import com.scholarsync.model.Admin;
import com.scholarsync.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds the admin account on first startup if it doesn't exist.
 * Default credentials: username=admin, password=admin123
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.findByUsername("admin").isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@upm.edu.my");
            adminRepository.save(admin);
            System.out.println("✅ Default admin created — username: admin / password: admin123");
        }
    }
}
