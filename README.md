<div align="center">

<img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
<img src="https://img.shields.io/badge/Spring_Boot-3.4.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
<img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
<img src="https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
<img src="https://img.shields.io/badge/File_Upload-Multipart-FF6B35?style=for-the-badge&logo=files&logoColor=white" alt="File Upload"/>

<br/><br/>

# 🎓 Student Management System

### A production-ready RESTful backend for managing the full academic lifecycle — students, courses, instructors, departments, and enrollments — with image upload support. Built with Java 21 and Spring Boot 3.

<br/>

[![GitHub Stars](https://img.shields.io/github/stars/hammad04x/Students-Management-System-In-Java-Spring-Boot?style=social)](https://github.com/hammad04x/Students-Management-System-In-Java-Spring-Boot/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/hammad04x/Students-Management-System-In-Java-Spring-Boot?style=social)](https://github.com/hammad04x/Students-Management-System-In-Java-Spring-Boot/network/members)
[![GitHub Issues](https://img.shields.io/github/issues/hammad04x/Students-Management-System-In-Java-Spring-Boot)](https://github.com/hammad04x/Students-Management-System-In-Java-Spring-Boot/issues)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Features](#-features)
- [Image Upload — FileStorageService](#-image-upload--filestorageservice)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Getting Started](#-getting-started)
- [Running the Application](#-running-the-application)
- [Testing with Postman](#-testing-with-postman)
- [Project Structure](#-project-structure)
- [Author](#-author)

---

## 🌟 Overview

The **Student Management System (SMS)** is a backend REST API designed to handle real-world academic data management. It provides a clean, layered architecture with proper separation of concerns — from the controller layer all the way down to the DAO layer — built on top of Spring Boot 3 and MySQL.

This project demonstrates:
- Enterprise-grade layered architecture (Controller → Service → DAO → Entity)
- JPA/Hibernate relationships (OneToMany, ManyToMany, ManyToOne)
- DTO pattern for clean API contracts
- **Multipart file upload with `FileStorageService`** — UUID-based local disk storage
- **Student profile image** — upload on create, replace on update, auto-delete old file
- **Soft delete** — students are marked `DELETED` (not hard removed from DB)
- **Search with pagination** — keyword-based search across active students
- Bean Validation & AOP-based exception handling
- Postman Collection for instant API testing

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| 🟤 **Language** | Java 21 |
| 🍃 **Framework** | Spring Boot 3.4.3 |
| 🗄️ **Database** | MySQL 8 |
| 🔗 **ORM** | Spring Data JPA / Hibernate |
| ✅ **Validation** | Spring Boot Validation (Jakarta) |
| 🔍 **AOP** | Spring Boot AOP |
| 📁 **File Storage** | Local Disk via `FileStorageService` (Multipart) |
| ⚙️ **Build Tool** | Apache Maven 3.9 |
| 📦 **Boilerplate** | Lombok 1.18.36 |
| 🧪 **Testing** | Postman (Collection included) |

---

## 🏗 Architecture

```
┌─────────────────────────────────────────────────────┐
│                    HTTP Client                       │
│         (Postman / Frontend — multipart/form-data)   │
└────────────────────┬────────────────────────────────┘
                     │ REST API
┌────────────────────▼────────────────────────────────┐
│              Controller Layer                        │
│   @RestController  │  Request Mapping  │  DTOs IN   │
│   @RequestPart (DTO + MultipartFile image)           │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│               Service Layer                          │
│    Business Logic  │  DTO Mapping  │  Validation     │
│    StudentService  │  FileStorageService             │
└────────────────────┬────────────────────────────────┘
                     │
          ┌──────────┴──────────┐
          │                     │
┌─────────▼────────┐   ┌────────▼────────────────────┐
│    DAO Layer     │   │     FileStorageService        │
│  Spring Data JPA │   │  UUID rename → local disk     │
│  Repositories    │   │  uploads/students/{uuid}.ext  │
└─────────┬────────┘   └─────────────────────────────┘
          │
┌─────────▼────────────────────────────────────────────┐
│              MySQL Database                           │
│  Student: id, name, email, phoneNo, imagePath, status │
│  Course, Instructor, Department, Enrollment           │
└──────────────────────────────────────────────────────┘
```

---

## ✨ Features

### 👨‍🎓 Student Management
- Create student with optional **profile image upload**
- Paginated student listing (active students only)
- Find student by ID
- Update student — replaces old image from disk automatically
- Soft delete — sets status to `DELETED`, preserves DB record
- Keyword search across students with pagination

### 📚 Course Management
- Full CRUD for courses
- Assign instructors to courses
- Track enrolled students per course

### 👨‍🏫 Instructor Management
- Manage instructor profiles
- Associate instructors with departments
- Link instructors to courses they teach

### 🏫 Department Management
- Organize courses by department
- Track department-level instructors

### 📋 Enrollment Management
- Manage student-course enrollment records
- Enforce enrollment rules via business logic

### ⚙️ Cross-Cutting Concerns
- Global exception handling via `@ControllerAdvice`
- AOP-based logging
- Bean Validation on request bodies
- Clean DTO ↔ Entity mapping
- `ResponseModel` wrapper for consistent API responses

---

## 📁 Image Upload — FileStorageService

Students support an optional **profile image** that is stored on the local disk server.

### How It Works

```
Client sends multipart/form-data request
         │
         ▼
StudentService checks if image is present
         │
         ▼
FileStorageService.storeFile(MultipartFile)
  1. Reads upload dir from config → app.upload.dir (default: uploads/students)
  2. Creates directory if it doesn't exist
  3. Extracts original file extension (.jpg, .png, etc.)
  4. Generates a UUID-based unique filename → e.g. a3f9c1d2-...jpg
  5. Copies file bytes to disk
  6. Returns the stored path → uploads/students/{uuid}.ext
         │
         ▼
StudentDTO.imagePath = stored path
Saved to Student entity in MySQL
```

### On Update — Auto Image Replacement

When a student is updated with a new image:
1. The **old image file is deleted from disk** (`Files.deleteIfExists`)
2. The **new image is stored** with a fresh UUID filename
3. The new path is saved to the DB

```java
// Old image cleanup (StudentService.updateTheStudent)
if (student.getImagePath() != null) {
    Path oldImagePath = Paths.get(student.getImagePath());
    Files.deleteIfExists(oldImagePath);
}
// Then store new image
String newImagePath = fileStorageService.storeFile(image);
```

### Configuration

Set the upload directory in `application.properties`:

```properties
# Default: uploads/students (relative to project root)
app.upload.dir=uploads/students

# Or use an absolute path
app.upload.dir=/var/app/uploads/students
```

### Stored File Structure

```
project-root/
└── uploads/
    └── students/
        ├── a3f9c1d2-4e8b-11ef-9f2a-0242ac120002.jpg
        ├── b7d2e4f1-5c9a-22fg-8h3b-1353bd231113.png
        └── ...
```

### Request Format (Multipart)

When calling create or update student endpoints, send as `multipart/form-data`:

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `dto` | JSON part | ✅ Yes | Student data (name, email, phoneNo, etc.) |
| `image` | File part | ❌ Optional | Profile image (jpg, png, etc.) |

---

## 🔌 API Endpoints

### 👨‍🎓 Students

| Method | Endpoint | Description | Content-Type |
|--------|----------|-------------|--------------|
| `GET` | `/api/students?pageSize=10&pageNo=1` | Get all active students (paginated) | — |
| `GET` | `/api/students/{id}` | Get student by ID | — |
| `POST` | `/api/students` | Create student (with optional image) | `multipart/form-data` |
| `PUT` | `/api/students/{id}` | Update student (replaces image if provided) | `multipart/form-data` |
| `DELETE` | `/api/students/{id}` | Soft delete student | — |
| `GET` | `/api/students/search?keyword=X&pageSize=10&pageNo=1` | Search students by keyword | — |

### 📚 Courses

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/courses` | Get all courses |
| `GET` | `/api/courses/{id}` | Get course by ID |
| `POST` | `/api/courses` | Create a new course |
| `PUT` | `/api/courses/{id}` | Update course details |
| `DELETE` | `/api/courses/{id}` | Delete a course |

### 👨‍🏫 Instructors

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/instructors` | Get all instructors |
| `GET` | `/api/instructors/{id}` | Get instructor by ID |
| `POST` | `/api/instructors` | Create a new instructor |
| `PUT` | `/api/instructors/{id}` | Update instructor |
| `DELETE` | `/api/instructors/{id}` | Delete an instructor |

### 🏫 Departments

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/departments` | Get all departments |
| `GET` | `/api/departments/{id}` | Get department by ID |
| `POST` | `/api/departments` | Create a department |
| `PUT` | `/api/departments/{id}` | Update department |
| `DELETE` | `/api/departments/{id}` | Delete a department |

### 📋 Enrollments

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/enrollments` | Get all enrollments |
| `POST` | `/api/enrollments` | Enroll a student in a course |
| `DELETE` | `/api/enrollments/{id}` | Remove enrollment |

---

## 🗃 Database Schema

```
┌─────────────┐         ┌──────────────┐         ┌───────────────┐
│  Department │◄────────│  Instructor  │────────►│    Course     │
│─────────────│  1    N │──────────────│  N    1 │───────────────│
│ id          │         │ id           │         │ id            │
│ name        │         │ firstName    │         │ title         │
│             │         │ lastName     │         │ credits       │
└─────────────┘         │ email        │         │ instructor_id │
                        └──────────────┘         └──────┬────────┘
                                                         │
                                                         │ N
                                                  ┌──────▼────────┐
                                                  │  Enrollment   │
┌──────────────────┐                              │───────────────│
│     Student      │◄─────────────────────────────│ id            │
│──────────────────│  1                        N  │ student_id    │
│ id               │                              │ course_id     │
│ firstName        │                              │ enrolledAt    │
│ lastName         │                              └───────────────┘
│ email            │
│ phoneNo          │
│ imagePath        │  ← stores path: uploads/students/{uuid}.ext
│ status           │  ← ACTIVE | DELETED (soft delete)
└──────────────────┘
```

---

## 🚀 Getting Started

### Prerequisites

```bash
☕ Java 21+
🐬 MySQL 8.0+
🔧 Maven 3.9+
```

### Clone the Repository

```bash
git clone https://github.com/hammad04x/Students-Management-System-In-Java-Spring-Boot.git
cd Students-Management-System-In-Java-Spring-Boot
```

### Configure the Database

Create your MySQL database:

```sql
CREATE DATABASE sms_db;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sms_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# File upload config
app.upload.dir=uploads/students

# Increase max file size if needed
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=10MB
```

---

## ▶️ Running the Application

### Using Maven Wrapper

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

### Using Maven directly

```bash
mvn spring-boot:run
```

### Build JAR and Run

```bash
mvn clean package
java -jar target/SMS-0.0.1-SNAPSHOT.jar
```

The server will start at:
```
http://localhost:8080
```

Uploaded images will be saved at:
```
{project-root}/uploads/students/
```

---

## 🧪 Testing with Postman

A Postman collection is included in the root of this project:

```
SMS.postman_collection.json
```

To use it:
1. Open Postman
2. Click **Import**
3. Select `SMS.postman_collection.json`
4. All API requests are pre-configured and ready to test

### Testing Image Upload in Postman

For `POST /api/students` and `PUT /api/students/{id}`:

1. Set request type to `POST`
2. Go to **Body** → select **form-data**
3. Add key `dto` → set type to **Text** → paste JSON:
```json
{
  "firstName": "Hammad",
  "lastName": "Jagarala",
  "email": "hammad@example.com",
  "phoneNo": "9876543210"
}
```
4. Add key `image` → set type to **File** → select an image file
5. Hit **Send**

---

## 📁 Project Structure

```
SMS/
├── src/
│   └── main/
│       ├── java/com/example/SMS/
│       │   ├── controller/          # REST Controllers
│       │   ├── service/
│       │   │   ├── StudentService.java      # Core student logic + image handling
│       │   │   └── FileStorageService.java  # UUID rename + local disk storage
│       │   ├── dao/                 # Data Access (JPA Repositories)
│       │   ├── entity/
│       │   │   ├── Student.java     # includes imagePath + Status enum
│       │   │   └── enums/
│       │   │       └── Status.java  # ACTIVE | DELETED
│       │   ├── dto/                 # Data Transfer Objects
│       │   ├── util/
│       │   │   └── APIMessage.java  # Centralized API message constants
│       │   ├── exception/           # Custom Exceptions & Global Handler
│       │   └── SmsApplication.java  # Entry Point
│       └── resources/
│           └── application.properties
├── uploads/
│   └── students/                    # Auto-created — stores student images
│       └── {uuid}.jpg / {uuid}.png
├── SMS.postman_collection.json      # Postman API Collection
├── pom.xml
└── README.md
```

---

## 👨‍💻 Author

<div align="center">

**Hammad**

[![GitHub](https://img.shields.io/badge/GitHub-hammad04x-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/hammad04x)
[![Portfolio](https://img.shields.io/badge/Portfolio-Live-00C7B7?style=for-the-badge&logo=vercel&logoColor=white)](https://jagaralahammad.vercel.app)

*Building scalable backend systems with Java & Spring Boot*

</div>

---

<div align="center">

⭐ **If this project helped you, drop a star — it means a lot!** ⭐

</div>
