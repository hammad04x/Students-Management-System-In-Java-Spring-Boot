package com.example.sms.dao;

import com.example.sms.dao.repo.CourseRepo;
import com.example.sms.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CourseDao extends BasicCURD<Course, String> {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    public CourseDao(CourseRepo courseRepo) {
        super(courseRepo);
        this.courseRepo = courseRepo;
    }
}
