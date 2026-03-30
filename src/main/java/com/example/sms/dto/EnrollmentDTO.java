package com.example.sms.dto;


import com.example.sms.entity.Course;
import com.example.sms.entity.Enrollment;
import com.example.sms.entity.Student;
import com.example.sms.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentDTO {

    private String enrollmentId;
    private LocalDate enrollmentDate;

    @NotBlank(message = "Student Id Cannot Be Null")
    private String studentId;

    @NotBlank(message = "Course Id Cannot Be Null")
    private String courseId;


    public static EnrollmentDTO toDto(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setEnrollmentId(enrollment.getEnrollmentId());
        enrollmentDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
        enrollmentDTO.setStudentId(enrollment.getStudent().getStudentId());
        enrollmentDTO.setCourseId(enrollment.getCourse().getCourseId());
        return enrollmentDTO;
    }

    public Enrollment toUpdateEntity(Enrollment entity, Student student, Course course) {
        if (this.studentId != null) {
            entity.setStudent(student);
        }
        if (this.courseId != null) {
            entity.setCourse(course);
        }
        return entity;
    }


}
