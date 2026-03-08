package com.example.sms.entity;


import com.example.sms.entity.enums.Gender;
import com.example.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate DOB;
    private String email;
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        this.studentId = UUID.randomUUID().toString();
        this.setStatus(Status.ACTIVE);
    }
}
