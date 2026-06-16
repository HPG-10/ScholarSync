-- ============================================================
-- ScholarSync Database Setup Script
-- Run this in MySQL Workbench OR SQL Developer
-- ============================================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS scholarsync_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE scholarsync_db;

-- ============================================================
-- Step 2: Create Tables
-- ============================================================

-- Admin table
CREATE TABLE IF NOT EXISTS admin (
    admin_id   INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE
);

-- Student table
CREATE TABLE IF NOT EXISTS student (
    student_id   INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    cgpa         DECIMAL(3,2) NOT NULL,
    faculty      VARCHAR(100),
    nationality  VARCHAR(50),
    income_level ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'LOW',
    password     VARCHAR(255) NOT NULL
);

-- Scholarship table
CREATE TABLE IF NOT EXISTS scholarship (
    scholarship_id INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(150) NOT NULL,
    provider       VARCHAR(100),
    min_cgpa       DECIMAL(3,2),
    faculty        VARCHAR(100),   -- NULL = open to all faculties
    deadline       DATE,
    amount         DECIMAL(10,2),
    description    TEXT,
    admin_id       INT,
    CONSTRAINT fk_scholarship_admin FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);

-- Application table (bridge table: Student <-> Scholarship)
CREATE TABLE IF NOT EXISTS application (
    appl_id        INT AUTO_INCREMENT PRIMARY KEY,
    student_id     INT NOT NULL,
    scholarship_id INT NOT NULL,
    date_applied   DATE DEFAULT (CURRENT_DATE),
    status         ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    CONSTRAINT fk_app_student    FOREIGN KEY (student_id)     REFERENCES student(student_id),
    CONSTRAINT fk_app_scholarship FOREIGN KEY (scholarship_id) REFERENCES scholarship(scholarship_id)
);

-- Eligibility Criteria table
CREATE TABLE IF NOT EXISTS eligibility_criteria (
    criteria_id    INT AUTO_INCREMENT PRIMARY KEY,
    scholarship_id INT NOT NULL,
    criteria_type  VARCHAR(50),    -- e.g. 'cgpa', 'nationality', 'faculty', 'income_level'
    criteria_value VARCHAR(100),   -- e.g. '3.50', 'Malaysian', 'Engineering'
    CONSTRAINT fk_criteria_scholarship FOREIGN KEY (scholarship_id) REFERENCES scholarship(scholarship_id)
);

-- ============================================================
-- Step 3: Insert Sample Data
-- ============================================================

-- Admin account
-- Password is: admin123 (BCrypt hashed)
INSERT INTO admin (username, password, email) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyBGGHB2y', 'admin@upm.edu.my');

-- Sample scholarships
INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id) VALUES
('UPM Excellence Award',
 'Universiti Putra Malaysia',
 3.50, NULL, '2025-12-31', 5000.00,
 'Awarded to top-performing UPM students across all faculties. Open to Malaysian and international students with outstanding academic achievement.',
 1),

('Yayasan Khazanah Scholar',
 'Yayasan Khazanah',
 3.70, NULL, '2025-06-30', 15000.00,
 'Prestigious scholarship for high-achieving students with proven leadership qualities.',
 1),

('MARA Digital Scholarship',
 'MARA',
 3.00, 'Computer Science', '2025-09-30', 8000.00,
 'Supporting Bumiputera students in Computer Science and IT-related fields.',
 1),

('Shell Malaysia Scholarship',
 'Shell Malaysia',
 3.50, 'Engineering', '2025-08-31', 12000.00,
 'For Engineering students demonstrating technical excellence and community involvement.',
 1),

('B40 Student Support Fund',
 'Ministry of Higher Education',
 2.50, NULL, '2025-12-31', 3000.00,
 'Financial support for students from low-income (B40) households.',
 1),

('Sime Darby Foundation Award',
 'Sime Darby Foundation',
 3.20, 'Agriculture', '2025-10-31', 7000.00,
 'Supports students in Agriculture and related sciences at UPM.',
 1);

-- Eligibility criteria examples
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES
(1, 'nationality', 'Malaysian'),
(1, 'cgpa', '3.50'),
(3, 'nationality', 'Malaysian'),
(3, 'faculty', 'Computer Science'),
(4, 'faculty', 'Engineering'),
(4, 'cgpa', '3.50'),
(5, 'income_level', 'LOW'),
(6, 'faculty', 'Agriculture');

-- ============================================================
-- Step 4: Verification Queries
-- ============================================================

SELECT 'Tables created:' AS info;
SHOW TABLES;

SELECT 'Scholarships:' AS info;
SELECT scholarship_id, name, provider, min_cgpa, faculty, amount FROM scholarship;

SELECT 'Admin account:' AS info;
SELECT admin_id, username, email FROM admin;
