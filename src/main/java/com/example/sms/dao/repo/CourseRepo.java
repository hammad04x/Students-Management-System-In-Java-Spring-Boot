package com.example.sms.dao.repo;

import com.example.sms.entity.Course;
import com.example.sms.entity.enums.Category;
import com.example.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepo extends JpaRepository<Course, String> {

    boolean existsByCourseName(String courseName);

    Page<Course> findAllCourseByStatus(Status status, Pageable pageable);

    Page<Course> findAllByCategory(Category category, Pageable pageable);

}
