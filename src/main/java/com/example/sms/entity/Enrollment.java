package com.example.sms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Enrollment {

    @Id
    private String enrollmentId;
    private LocalDate enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course courseId;

    @PrePersist
    public void prePersist() {
        this.enrollmentId = UUID.randomUUID().toString();
        this.enrollmentDate = LocalDate.now();
    }
}
