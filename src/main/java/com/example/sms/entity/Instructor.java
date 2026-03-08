package com.example.sms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Instructor {

    @Id
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    @PrePersist
    public void prePersist() {
        this.instructorId = UUID.randomUUID().toString();
    }
}

