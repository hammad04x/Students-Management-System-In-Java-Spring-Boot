package com.example.sms.controller;

import com.example.sms.dto.ResponseModel;
import com.example.sms.dto.StudentDTO;
import com.example.sms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sms/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/insert")
    public ResponseModel insertStudent(@RequestBody StudentDTO dto) {
        return studentService.saveTheStudent(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllStudents(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                              @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return studentService.getAllStudents(pageSize, pageNo);
    }

    @GetMapping("/{id}")
    public ResponseModel getStudentsById(@PathVariable String id) {
        return studentService.findStudentById(id);
    }
}
