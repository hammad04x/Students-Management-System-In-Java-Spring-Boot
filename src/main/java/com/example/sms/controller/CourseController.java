package com.example.sms.controller;

import com.example.sms.dto.CourseDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/insert")
    public ResponseModel insertCourse(@RequestBody CourseDTO courseDTO) {
        log.info("Insert Course Api Triggered");
        return courseService.insertCourse(courseDTO);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllCourses(@RequestParam(required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return courseService.getAllCourse(pageSize, pageNo);
    }

    @GetMapping("/by/{id}")
    public ResponseModel getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseModel updateCourseById(@PathVariable String id,
                                          @RequestBody CourseDTO dto) {
        return courseService.updateCourseById(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteCourseById(@PathVariable String id) {
        return courseService.deleteCourseById(id);
    }

    @GetMapping("/by/category/{name}")
    public ResponseModel getCourseByCategory(@PathVariable String name,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize,
                                             @RequestParam(required = false, defaultValue = "1") int pageNo) {

        return courseService.filterByCategory(name, pageNo, pageSize);
    }

}
