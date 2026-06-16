-- ============================================================
-- ScholarSync Database Setup Script (Oracle Compatible)
-- Run this in SQL Developer connected to Oracle
-- ============================================================

-- Table: ADMIN
CREATE TABLE admin (
    admin_id   NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username   VARCHAR2(50)  NOT NULL UNIQUE,
    password   VARCHAR2(255) NOT NULL,
    email      VARCHAR2(100) NOT NULL UNIQUE
);

-- Table: STUDENT
CREATE TABLE student (
    student_id   NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         VARCHAR2(100) NOT NULL,
    email        VARCHAR2(100) NOT NULL UNIQUE,
    cgpa         NUMBER(3,2)   NOT NULL,
    faculty      VARCHAR2(100),
    nationality  VARCHAR2(50),
    income_level VARCHAR2(10)  DEFAULT 'LOW',
    password     VARCHAR2(255) NOT NULL
);

-- Table: SCHOLARSHIP
CREATE TABLE scholarship (
    scholarship_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name           VARCHAR2(150) NOT NULL,
    provider       VARCHAR2(100),
    min_cgpa       NUMBER(3,2),
    faculty        VARCHAR2(100),
    deadline       DATE,
    amount         NUMBER(10,2),
    description    CLOB,
    admin_id       NUMBER,
    CONSTRAINT fk_scholarship_admin FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);

-- Table: APPLICATION
CREATE TABLE application (
    appl_id        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id     NUMBER NOT NULL,
    scholarship_id NUMBER NOT NULL,
    date_applied   DATE   DEFAULT SYSDATE,
    status         VARCHAR2(20) DEFAULT 'PENDING',
    CONSTRAINT fk_app_student     FOREIGN KEY (student_id)     REFERENCES student(student_id),
    CONSTRAINT fk_app_scholarship FOREIGN KEY (scholarship_id) REFERENCES scholarship(scholarship_id)
);

-- Table: ELIGIBILITY_CRITERIA
CREATE TABLE eligibility_criteria (
    criteria_id    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    scholarship_id NUMBER NOT NULL,
    criteria_type  VARCHAR2(50),
    criteria_value VARCHAR2(100),
    CONSTRAINT fk_criteria_scholarship FOREIGN KEY (scholarship_id) REFERENCES scholarship(scholarship_id)
);

-- ============================================================
-- Insert Sample Data
-- ============================================================

-- Admin (password = admin123, BCrypt hashed)
INSERT INTO admin (username, password, email) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyBGGHB2y', 'admin@upm.edu.my');

-- Scholarships
INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id)
VALUES ('UPM Excellence Award', 'Universiti Putra Malaysia', 3.50, NULL, TO_DATE('2025-12-31','YYYY-MM-DD'), 5000.00, 'Awarded to top-performing UPM students across all faculties.', 1);

INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id)
VALUES ('Yayasan Khazanah Scholar', 'Yayasan Khazanah', 3.70, NULL, TO_DATE('2025-06-30','YYYY-MM-DD'), 15000.00, 'Prestigious scholarship for high-achieving students with proven leadership.', 1);

INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id)
VALUES ('MARA Digital Scholarship', 'MARA', 3.00, 'Computer Science', TO_DATE('2025-09-30','YYYY-MM-DD'), 8000.00, 'Supporting Bumiputera students in Computer Science and IT-related fields.', 1);

INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id)
VALUES ('Shell Malaysia Scholarship', 'Shell Malaysia', 3.50, 'Engineering', TO_DATE('2025-08-31','YYYY-MM-DD'), 12000.00, 'For Engineering students demonstrating technical excellence.', 1);

INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id)
VALUES ('B40 Student Support Fund', 'Ministry of Higher Education', 2.50, NULL, TO_DATE('2025-12-31','YYYY-MM-DD'), 3000.00, 'Financial support for students from low-income (B40) households.', 1);

INSERT INTO scholarship (name, provider, min_cgpa, faculty, deadline, amount, description, admin_id)
VALUES ('Sime Darby Foundation Award', 'Sime Darby Foundation', 3.20, 'Agriculture', TO_DATE('2025-10-31','YYYY-MM-DD'), 7000.00, 'Supports students in Agriculture and related sciences at UPM.', 1);

-- Eligibility Criteria
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (1, 'nationality', 'Malaysian');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (1, 'cgpa', '3.50');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (3, 'nationality', 'Malaysian');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (3, 'faculty', 'Computer Science');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (4, 'faculty', 'Engineering');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (4, 'cgpa', '3.50');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (5, 'income_level', 'LOW');
INSERT INTO eligibility_criteria (scholarship_id, criteria_type, criteria_value) VALUES (6, 'faculty', 'Agriculture');

COMMIT;

-- Verify
SELECT * FROM scholarship;
SELECT * FROM admin;
