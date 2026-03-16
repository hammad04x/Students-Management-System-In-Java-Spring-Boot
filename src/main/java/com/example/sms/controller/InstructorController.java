package com.example.sms.controller;

import com.example.sms.dto.InstructorDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Instructor;
import com.example.sms.services.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/instructor")
public class InstructorController {

    @Autowired
    public InstructorService instructorService;

    @PostMapping("/insert")
    public ResponseModel insertInstructor(@RequestBody InstructorDTO dto) {
        return instructorService.insertInstructor(dto);
    }



}
