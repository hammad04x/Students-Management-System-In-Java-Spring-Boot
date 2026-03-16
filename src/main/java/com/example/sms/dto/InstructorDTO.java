package com.example.sms.dto;

import com.example.sms.entity.Department;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Status;
import lombok.Data;

@Data
public class InstructorDTO {

    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Status status;

    public Instructor toEntity() {
        return Instructor.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .build();
    }

    public static InstructorDTO toDTO(Instructor instructor) {
        InstructorDTO dto = new InstructorDTO();
        dto.setInstructorId(instructor.getInstructorId());
        dto.setFirstName(instructor.getFirstName());
        dto.setLastName(instructor.getLastName());
        dto.setEmail(instructor.getEmail());
        dto.setPhoneNo(instructor.getPhoneNo());
        dto.setStatus(instructor.getStatus());
        return dto;
    }

    public Instructor toUpdateEntity(Instructor instructor) {
        if (this.firstName != null) {
            instructor.setFirstName(this.firstName);
        }
        if (this.lastName != null) {
            instructor.setLastName(this.lastName);
        }
        if (this.email != null) {
            instructor.setEmail(this.email);
        }
        if (this.phoneNo != null) {
            instructor.setPhoneNo(this.phoneNo);
        }
        if (this.status != null) {
            instructor.setStatus(this.status);
        }
        return instructor;
    }
}
