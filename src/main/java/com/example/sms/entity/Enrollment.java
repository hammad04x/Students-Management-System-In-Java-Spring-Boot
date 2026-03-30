package com.example.sms.entity;

import com.example.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"studentId", "courseId"})
        }
)
public class Enrollment {

    @Id
    private String enrollmentId;
    private LocalDate enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;


    @PrePersist
    public void prePersist() {
        this.enrollmentId = UUID.randomUUID().toString();
        this.enrollmentDate = LocalDate.now();
    }
}
