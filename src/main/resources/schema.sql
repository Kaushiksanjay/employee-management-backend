CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_management;

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(120) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    designation VARCHAR(100) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE'
);

-- Assessment query 1: Retrieve all active employees
SELECT * FROM employees WHERE status = 'ACTIVE';

-- Assessment query 2: Count employees in each department
SELECT department, COUNT(*) AS employee_count
FROM employees
GROUP BY department;

-- Assessment query 3: Find employees whose names contain a search term
SELECT * FROM employees WHERE full_name LIKE '%john%';

-- Assessment query 4: Identify duplicate email addresses in an existing dataset
SELECT email, COUNT(*) AS occurrences
FROM employees
GROUP BY email
HAVING COUNT(*) > 1;
