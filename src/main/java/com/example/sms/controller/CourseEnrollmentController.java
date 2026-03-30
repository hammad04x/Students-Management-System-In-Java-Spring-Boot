package com.example.sms.controller;

import com.example.sms.dto.EnrollmentDTO;
import com.example.sms.dto.InstructorDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.services.CourseEnrollmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/enrollment")
public class CourseEnrollmentController {

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @PostMapping("/insert")
    public ResponseModel insertEnrollment(@RequestBody @Valid EnrollmentDTO dto) {
        return courseEnrollmentService.insertEnrollment(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAll(@RequestParam(required = false, defaultValue = "10") int pageSize,
                                @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return courseEnrollmentService.getAll(pageSize, pageNo);
    }

    @GetMapping("/by/{enrollmentId}")
    public ResponseModel getEnrollmentById(@PathVariable String enrollmentId) {
        return courseEnrollmentService.getEnrollmentById(enrollmentId);
    }

    @PutMapping("/update/by/{enrollmentId}")
    public ResponseModel updateEnrollmentById(@PathVariable String enrollmentId, @RequestBody EnrollmentDTO dto) {
        return courseEnrollmentService.updateEnrollmentById(enrollmentId, dto);
    }

    @DeleteMapping("/delete/by/{enrollmentId}")
    public ResponseModel deleteEnrollmentById(@PathVariable String enrollmentId) {
        return courseEnrollmentService.deleteEnrollmentById(enrollmentId);
    }

}
