package com.example.sms.dto;

import com.example.sms.entity.Course;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.Student;
import com.example.sms.entity.enums.Category;
import com.example.sms.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {

    private String courseId;
    private String courseName;
    private String courseDescription;
    private long duration;
    private Status status;
    private Category category;
    private LocalDate createdOn;
    private String instructorId;

    public Course toEntity(Instructor instructor) {
        return Course.builder()
                .courseName(this.courseName)
                .courseDescription(this.courseDescription)
                .duration(this.duration)
                .status(this.status)
                .category(this.category)
                .instructorId(instructor)
                .build();
    }

    public static CourseDTO toDTO(Course course) {
        return CourseDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .courseDescription(course.getCourseDescription())
                .duration(course.getDuration())
                .status(course.getStatus())
                .category(course.getCategory())
                .instructorId(course.getInstructorId().getInstructorId())
                .createdOn(course.getCreatedOn())
                .build();
    }

    public Course toUpdateEntity(Course entity) {

        if (this.courseName != null) {
            entity.setCourseName(this.courseName);
        }
        if (this.courseDescription != null) {
            entity.setCourseDescription(this.courseDescription);
        }
        if (this.duration > 0) {
            entity.setDuration(this.duration);
        }
        if (this.category != null) {
            entity.setCategory(this.category);
        }
        if (this.status != null) {
            entity.setStatus(this.status);
        }



        return entity;
    }

}
