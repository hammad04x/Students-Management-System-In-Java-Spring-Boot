package com.example.sms.entity;

import com.example.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Department {
    @Id
    private String departmentId;
    private String departmentName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "hod")
    private Instructor HOD;

    @OneToMany
    private List<Instructor> instructors;

    @PrePersist
    public void prePersist() {
        this.departmentId = UUID.randomUUID().toString();
        this.setStatus(Status.ACTIVE);
    }
}