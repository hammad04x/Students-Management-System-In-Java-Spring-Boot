package com.example.sms.controller;

import com.example.sms.dto.InstructorDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Instructor;
import com.example.sms.services.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;
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

    @GetMapping("/get-all")
    public ResponseModel getAllInstructors(@RequestParam(required = false, defaultValue = "10") int pageSize,
                                           @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return instructorService.getAllInstructors(pageSize, pageNo);
    }

    @GetMapping("/by/{instructorId}")
    public ResponseModel getInstructorById(@PathVariable String instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    @PutMapping("/update/by/{instructorId}")
    public ResponseModel updateInstructorById(@PathVariable String instructorId,
                                              @RequestBody InstructorDTO dto) {
        return instructorService.updateInstructorById(instructorId, dto);
    }

    @DeleteMapping("/delete/by/{instructorId}")
    public ResponseModel deleteInstructorById(@PathVariable String instructorId) {
        return instructorService.deleteInstructorById(instructorId);
    }

    @GetMapping("/search")
    public ResponseModel searchInstructor(@RequestParam String keyword,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return instructorService.searchInstructor(keyword, pageSize, pageNo);
    }

}
