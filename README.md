# Legacy Code Challenge - Employee Management System

## Deskripsi Aplikasi
Project ini merupakan hasil refactoring dari aplikasi Java Swing legacy.

Versi awal memiliki masalah:
- Semua kode berada di JFrame
- SQL ditulis langsung di event button
- Tidak ada pemisahan entity, repository, service, controller, dan view
- Validasi input belum terstruktur
- Kode sulit dirawat

Setelah refactor, aplikasi dipisahkan menjadi:
- entity
- repository
- service
- controller
- view
- database
- utils

## Fitur Aplikasi
- Login
- Dashboard statistik karyawan
- CRUD data karyawan
- Pencarian data karyawan
- Laporan data karyawan

## Teknologi
- Java
- Java Swing
- JDBC
- MySQL
- Git
- GitHub

## Struktur Project
src/
- entity: class model data
- repository: akses database dan SQL query
- service: logic aplikasi dan validasi
- controller: penghubung view dan service
- view: tampilan Java Swing
- database: koneksi database
- utils: helper dan validasi

## ERD
USERS
- id
- username
- password

EMPLOYEES
- id
- name
- position
- department
- salary

## Use Case
Admin dapat:
- Login
- Melihat dashboard
- Mengelola data karyawan
- Mencari data karyawan
- Melihat laporan karyawan

## Cara Install
1. Clone repository.
2. Import database.sql ke MySQL melalui phpMyAdmin.
3. Pastikan database db_legacy_refactor sudah tersedia.
4. Pastikan MySQL Connector/J tersedia di folder lib lokal.
5. Compile project:
   javac -d out -cp "lib/*" (Get-ChildItem -Recurse -Filter *.java -Path src).FullName
6. Jalankan aplikasi:
   java -cp "out;lib/*" Main

## Akun Login
Username: admin
Password: admin123

## Git Workflow
Pengembangan tidak dilakukan langsung di branch main.

Branch yang digunakan:
- main
- develop
- feature-login
- feature-dashboard
- feature-master
- feature-report

Tag:
- v1.0-legacy: versi sebelum refactor
- v2.0-clean: versi setelah refactor

## Ringkasan Refactoring

### Sebelum Refactor
- SQL berada langsung di JFrame
- Event button berisi logic aplikasi
- Kode sulit dibaca
- Tidak ada pemisahan tanggung jawab

### Setelah Refactor
- SQL dipindahkan ke repository
- Logic dipindahkan ke service
- View hanya menangani tampilan
- Controller menjadi penghubung view dan service
- Validasi input dipusatkan di service
- Struktur project lebih mudah dirawat

## Screenshot
Screenshot praktikum disimpan pada folder docs.
