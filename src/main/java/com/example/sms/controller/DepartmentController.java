package com.example.sms.controller;

import com.example.sms.dto.DepartmentDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/insert")
    public ResponseModel insertCourse(@RequestBody DepartmentDTO dto) {
        log.info("insertDepartment API Triggered");
        return departmentService.insertDepartment(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllDepartments(@RequestParam(required = false, defaultValue = "10") int pageSize,
                                           @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return departmentService.getAllDepartment(pageSize, pageNo);
    }

    @GetMapping("/by/{departmentId}")
    public ResponseModel getDepartmentById(@PathVariable String departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @PutMapping("/update/by/{departmentId}")
    public ResponseModel updateDepartmentById(@PathVariable String departmentId,
                                              @RequestBody DepartmentDTO dto) {
        return departmentService.updateById(departmentId, dto);
    }

    @DeleteMapping("/delete/by/{departmentId}")
    public ResponseModel deleteByDepartmentId(@PathVariable String departmentId) {
        return departmentService.deleteById(departmentId);
    }

    @GetMapping("/search")
    public ResponseModel searchDepartment(@RequestParam String serachKeyword,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(required = false, defaultValue = "1") int pageNo) {
        return departmentService.searchDepartment(serachKeyword, pageSize, pageNo);
    }


}
