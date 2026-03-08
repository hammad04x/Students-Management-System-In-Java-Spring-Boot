package com.example.sms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Department {

    @Id
    private String departmentId;
    private String departmentName;

    @OneToOne
    @JoinColumn(name = "hod")
    private Instructor HOD;

    @OneToMany
    @JoinColumn(name = "faculties")
    private List<Instructor> instructors;

    @PrePersist
    public void prePersist() {
        this.departmentId = UUID.randomUUID().toString();
    }
}
