package com.example.sms.dao.repo;

import com.example.sms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepo extends JpaRepository<Enrollment, String> {
    boolean existsByStudent_StudentIdAndCourse_CourseId(String studentId,String courseId);
}
