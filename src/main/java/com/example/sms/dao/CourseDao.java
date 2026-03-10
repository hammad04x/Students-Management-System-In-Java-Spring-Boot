package com.example.sms.dao;

import com.example.sms.dao.repo.CourseRepo;
import com.example.sms.entity.Course;
import com.example.sms.entity.enums.Category;
import com.example.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class CourseDao extends BasicCRUD<Course, String> {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    public CourseDao(CourseRepo courseRepo) {
        super(courseRepo);
        this.courseRepo = courseRepo;
    }

    public boolean existByName(String name) {
        return courseRepo.existsByCourseName(name);
    }

    public Page<Course> getAllCourseByStatus(Status status, Pageable pageable) {
        return courseRepo.findAllCourseByStatus(status, pageable);
    }

    public Page<Course> filterAllByCategory(Category category, Pageable pageable) {
        return courseRepo.findAllByCategory(category, pageable);
    }

}
