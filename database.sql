CREATE DATABASE IF NOT EXISTS db_legacy_refactor;
USE db_legacy_refactor;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    salary DOUBLE NOT NULL
);

INSERT INTO users (username, password) VALUES
('admin', 'admin123');

INSERT INTO employees (name, position, department, salary) VALUES
('Razzi Ronaldi', 'Programmer', 'IT', 3500000),
('Abdul Qohhar', 'Staff Admin', 'Operasional', 3000000),
('Yandri Utama', 'Data Entry', 'Keuangan', 2800000);
