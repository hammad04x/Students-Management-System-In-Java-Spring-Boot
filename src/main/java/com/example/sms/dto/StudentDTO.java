package com.example.sms.dto;

import com.example.sms.controller.validator.MaxYear;
import com.example.sms.entity.Student;
import com.example.sms.entity.enums.Gender;
import com.example.sms.entity.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO {

    private String studentId;

    @NotBlank(message = "First Name Is Required")
    @Size(min = 3, message = "At least 3 char name is required")
    private String fName;

    @NotBlank(message = "Last Name Is Required")
    @Size(min = 3, message = "At least 3 char name is required")
    private String lName;

    @NotNull(message = "Date Of Birth Is Required")
    @Past(message = "Date Of Birth Can't Be From Future")
    @MaxYear(value = 2020)
    private LocalDate DOB;

    @NotBlank(message = "Email Is Required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Phone No Is Required")
    @Pattern(regexp = "[0-9]{10}$", message = "Invalid Phone No")
    private String phoneNo;

    private String imagePath;

    @NotNull(message = "Gender Is Required")
    private Gender gender;

    private Status status;

    public StudentDTO(String studentId, String fName, String lName, LocalDate DOB, String email, String phoneNo, String imagePath, Gender gender, Status status) {
        this.studentId = studentId;
        this.fName = fName;
        this.lName = lName;
        this.DOB = DOB;
        this.email = email;
        this.phoneNo = phoneNo;
        this.imagePath = imagePath;
        this.gender = gender;
        this.status = status;
    }

    public Student toEntity() {
        return Student.builder()
                .firstName(this.fName)
                .lastName(this.lName)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .DOB(this.DOB)
                .imagePath(this.imagePath)
                .gender(this.gender)
                .build();
    }

    public static StudentDTO toDto(Student entity) {
        return new StudentDTO(
                entity.getStudentId().toString(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDOB(),
                entity.getEmail(),
                entity.getPhoneNo(),
                entity.getImagePath(),
                entity.getGender(),
                entity.getStatus()
        );
    }

    public Student toUpdateEntity(Student entity) {

        if (this.fName != null) {
            entity.setFirstName(this.fName);
        }
        if (this.lName != null) {
            entity.setLastName(this.lName);
        }
        if (this.email != null) {
            entity.setEmail(this.email);
        }
        if (this.phoneNo != null) {
            entity.setPhoneNo(this.phoneNo);
        }
        if (this.imagePath != null) {
            entity.setImagePath(this.imagePath);
        }
        if (this.DOB != null) {
            entity.setDOB(this.DOB);
        }
        if (this.gender != null) {
            entity.setGender(this.gender);
        }

        return entity;
    }
}
