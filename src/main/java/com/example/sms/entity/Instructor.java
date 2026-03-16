package com.example.sms.entity;

import com.example.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Instructor {

    @Id
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void prePersist() {
        this.instructorId = UUID.randomUUID().toString();
        this.setStatus(Status.ACTIVE);
    }

}

