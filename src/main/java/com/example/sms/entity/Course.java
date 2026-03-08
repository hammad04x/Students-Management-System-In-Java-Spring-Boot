package com.example.sms.entity;

import com.example.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Course {

    @Id
    private String courseId;
    private String courseName;
    private String courseDescription;
    private long duration;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdOn;

    @ManyToOne
    @JoinColumn(name = "instructorId")
    private Instructor instructorId;

    @PrePersist
    public void prePersist() {
        this.courseId = UUID.randomUUID().toString();
        this.createdOn = LocalDate.now();
    }
}
