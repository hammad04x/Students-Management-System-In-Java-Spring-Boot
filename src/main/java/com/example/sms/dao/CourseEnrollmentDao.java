package com.example.sms.dao;

import com.example.sms.dao.repo.EnrollmentRepo;
import com.example.sms.entity.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CourseEnrollmentDao extends BasicCRUD<Enrollment, String> {
    @Autowired
    private EnrollmentRepo enrollmentRepo;


    public CourseEnrollmentDao(EnrollmentRepo enrollmentRepo) {
        super(enrollmentRepo);
        this.enrollmentRepo = enrollmentRepo;
    }

    public Page<Enrollment> findAll(Pageable pageable) {
        return enrollmentRepo.findAll(pageable);
    }

    public boolean existsByStudent_StudentIdAndCourse_CourseId(String studentId, String courseId) {
        return enrollmentRepo.existsByStudent_StudentIdAndCourse_CourseId(studentId, courseId);
    }

}
