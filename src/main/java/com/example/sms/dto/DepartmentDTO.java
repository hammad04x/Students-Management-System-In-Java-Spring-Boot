package com.example.sms.dto;

import com.example.sms.entity.Department;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private String departmentId;
    private String departmentName;
    private Status status;
    private String hodId;
    private List<String> instructorIds;

    public Department toEntity(Instructor hod, List<Instructor> instructors) {
        return Department.builder()
                .departmentName(this.departmentName)
                .HOD(hod)
                .instructors(instructors)
                .build();
    }

    public static DepartmentDTO toDTO(Department department) {

        String hodId = null;
        if (department.getHOD() != null) {
            hodId = department.getHOD().getInstructorId();
        }

        List<String> instructorIds = new ArrayList<>();
        if (department.getInstructors() != null) {
            for (Instructor instructor : department.getInstructors()) {
                instructorIds.add(instructor.getInstructorId());
            }
        }

        return DepartmentDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .status(department.getStatus())
                .hodId(hodId)
                .instructorIds(instructorIds)
                .build();
    }

    public Department toUpdateEntity(Department entity, Instructor hod, List<Instructor> instructors) {
        if (this.departmentName != null) {
            entity.setDepartmentName(this.departmentName);
        }
        if (this.status != null) {
            entity.setStatus(this.status);
        }
        if (hod != null) {
            entity.setHOD(hod);
        }
        if (instructors != null && !instructors.isEmpty()) {
            entity.setInstructors(instructors);
        }
        return entity;
    }


}
