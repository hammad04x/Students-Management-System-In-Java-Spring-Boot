package com.example.sms.services;

import com.example.sms.dao.DepartmentDao;
import com.example.sms.dao.InstructorDao;
import com.example.sms.dto.DepartmentDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Department;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Status;
import com.example.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DepartmentService {
    @Autowired
    public DepartmentDao departmentDao;
    @Autowired
    public InstructorDao instructorDao;

    public ResponseModel insertDepartment(DepartmentDTO dto) {
        log.info("Insert Department Method Executed");

        boolean deparmentExists = departmentDao.existsByDepartment(dto.getDepartmentName());
        if (deparmentExists) {
            return ResponseModel.conflict(
                    APIMessage.DEPARTMENT_ALREADY_PRESENT,
                    null
            );
        }

        Instructor hod = null;
        if (dto.getHodId() != null) {
            hod = instructorDao.findById(dto.getHodId());
            if (hod == null) {
                return ResponseModel.not_found(
                        APIMessage.INSTRUCTOR_NOT_FOUND,
                        null
                );
            }
        }

        List<Instructor> instructors = new ArrayList<>();
        if (dto.getInstructorIds() != null && !dto.getInstructorIds().isEmpty()) {
            for (String instructorId : dto.getInstructorIds()) {
                Instructor instructor = instructorDao.findById(instructorId);
                if (instructor == null) {
                    return ResponseModel.not_found(
                            APIMessage.INSTRUCTOR_NOT_FOUND,
                            null
                    );
                }
                instructors.add(instructor);
            }
        }

        log.info("All Validation Done Ready To Insert new Department");

        Department department = dto.toEntity(hod, instructors);
        department = departmentDao.save(department);

        log.info("Insert Department Method Execution End Preparing Response");
        return ResponseModel.success(
                APIMessage.DEPARTMENT_CREATED,
                DepartmentDTO.toDTO(department)
        );

    }

    public ResponseModel getAllDepartment(int pageSize, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Department> departments = departmentDao.findAllByStatus(Status.ACTIVE, pageable);

        List<DepartmentDTO> dtos = departments.getContent()
                .stream()
                .map(DepartmentDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", departments.getTotalElements());
        pageResult.put("pageCount", departments.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.DEPARTMENT_FOUND,
                result
        );

    }

    public ResponseModel getDepartmentById(String departmentId) {
        Department department = departmentDao.findById(departmentId);

        if (department == null) {
            return ResponseModel.not_found(
                    APIMessage.DEPARTMENT_NOT_FOUND,
                    null
            );
        }

        return ResponseModel.success(
                APIMessage.DEPARTMENT_FOUND,
                DepartmentDTO.toDTO(department)
        );
    }

    public ResponseModel updateById(String departmentId, DepartmentDTO dto) {

        Department department = departmentDao.findById(departmentId);
        if (department == null) {
            return ResponseModel.not_found(
                    APIMessage.DEPARTMENT_NOT_FOUND,
                    null
            );
        }

        if (dto.getDepartmentName() != null && !dto.getDepartmentName().equals(department.getDepartmentName())) {
            boolean departmentExist = departmentDao.existsByDepartment(dto.getDepartmentName());
            if (departmentExist) {
                return ResponseModel.conflict(
                        APIMessage.DEPARTMENT_ALREADY_PRESENT,
                        null
                );
            }
        }

        Instructor hod = null;
        if (dto.getHodId() != null) {
            hod = instructorDao.findById(dto.getHodId());
            if (hod == null) {
                return ResponseModel.not_found(
                        APIMessage.INSTRUCTOR_NOT_FOUND,
                        null
                );
            }
        }

        List<Instructor> instructors = null;
        if (dto.getInstructorIds() != null && !dto.getInstructorIds().isEmpty()) {
            instructors = new ArrayList<>();
            for (String instructorId : dto.getInstructorIds()) {
                Instructor instructor = instructorDao.findById(instructorId);
                if (instructor == null) {
                    return ResponseModel.not_found(
                            APIMessage.INSTRUCTOR_NOT_FOUND,
                            null
                    );
                }
                instructors.add(instructor);
            }
        }

        department = dto.toUpdateEntity(department, hod, instructors);
        department = departmentDao.save(department);

        return ResponseModel.success(
                APIMessage.DEPARTMENT_UPDATED,
                DepartmentDTO.toDTO(department)
        );
    }

    public ResponseModel deleteById(String departmentId) {

        Department department = departmentDao.findById(departmentId);
        if (department == null) {
            return ResponseModel.not_found(
                    APIMessage.DEPARTMENT_NOT_FOUND,
                    null
            );
        }

        department.setStatus(Status.DELETED);
        departmentDao.save(department);
        return ResponseModel.success(
                APIMessage.DEPARTMENT_DELETED,
                DepartmentDTO.toDTO(department)
        );

    }

    public ResponseModel searchDepartment(String searchKeyword, int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Department> departments = departmentDao.searchDepartmentByStatus(searchKeyword, Status.ACTIVE, pageable);

        List<DepartmentDTO> dto = departments.getContent()
                .stream()
                .map(DepartmentDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", departments.getTotalElements());
        pageResult.put("totalPages", departments.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dto);
        result.put("pageResult",pageResult);

        return ResponseModel.success(
                APIMessage.DEPARTMENT_FOUND,
                result
        );
    }

}
