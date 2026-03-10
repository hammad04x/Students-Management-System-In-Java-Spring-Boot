package com.example.sms.services;

import com.example.sms.dao.StudentDao;
import com.example.sms.dto.ResponseModel;
import com.example.sms.dto.StudentDTO;
import com.example.sms.entity.Student;
import com.example.sms.entity.enums.Status;
import com.example.sms.util.APIMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public ResponseModel saveTheStudent(StudentDTO dto) {

        Boolean studentExists = studentDao.existsStudentByEmail(dto.getEmail());
        if (studentExists) {
            return ResponseModel.conflict(
                    APIMessage.STUDENT_ALREADY_PRESENT.formatted("Email"),
                    null
            );
        }

        studentExists = studentDao.existsStudentByPhoneNo(dto.getPhoneNo());
        if (studentExists) {
            return ResponseModel.conflict(
                    APIMessage.STUDENT_ALREADY_PRESENT.formatted("PhoneNo"),
                    null
            );
        }

        Student student = dto.toEntity();
        student = studentDao.save(student);
        return ResponseModel.created(
                APIMessage.STUDENT_CREATED,
                StudentDTO.toDto(student)
        );
    }

    public ResponseModel getAllStudents(int pageSize, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Student> students = studentDao.findAllStudentByStatus(Status.ACTIVE,pageable);

        List<StudentDTO> dtos = students.getContent()
                .stream()
                .map(StudentDTO::toDto)
                .toList();


        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", students.getTotalElements());
        pageResult.put("pageCount", students.getTotalPages());


        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.STUDENT_FOUND,
                result
        );
    }

    public ResponseModel findStudentById(String studentId) {

        Student student = studentDao.findById(studentId);

        if (student == null) {
            return ResponseModel.not_found(
                    APIMessage.STUDENT_NOT_FOUND,
                    null
            );
        }
        return ResponseModel.success(
                APIMessage.STUDENT_FOUND,
                StudentDTO.toDto(student)
        );

    }

    public ResponseModel updateTheStudent(String id, StudentDTO dto) {

        Student student = studentDao.findById(id);
        if (student == null) {
            ResponseModel.not_found(
                    APIMessage.STUDENT_NOT_FOUND,
                    null
            );
        }

        Boolean studentExists = studentDao.existsStudentByEmail(dto.getEmail());
        if (studentExists) {
            return ResponseModel.conflict(
                    APIMessage.STUDENT_ALREADY_PRESENT.formatted("Email"),
                    null
            );
        }

        studentExists = studentDao.existsStudentByPhoneNo(dto.getPhoneNo());
        if (studentExists) {
            return ResponseModel.conflict(
                    APIMessage.STUDENT_ALREADY_PRESENT.formatted("PhoneNo"),
                    null
            );
        }

        student = dto.toUpdateEntity(student);
        student = studentDao.save(student);
        return ResponseModel.success(
                APIMessage.STUDENT_UPDATED,
                StudentDTO.toDto(student)
        );
    }

    public ResponseModel deleteStudentById(String studentId) {

        Student student = studentDao.findById(studentId);

        if (student == null) {
            return ResponseModel.not_found(
                    APIMessage.STUDENT_NOT_FOUND,
                    null
            );
        }
        student.setStatus(Status.DELETED);
        studentDao.save(student);

        return ResponseModel.success(
                APIMessage.STUDENT_DELETED,
                StudentDTO.toDto(student)
        );

    }

    public ResponseModel searchStudent(String searchKeyword,int pageSize, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Student> students = studentDao.searchStudent(searchKeyword, Status.ACTIVE,pageable);

        List<StudentDTO> dtos = students.getContent()
                .stream()
                .map(StudentDTO::toDto)
                .toList();


        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", students.getTotalElements());
        pageResult.put("pageCount", students.getTotalPages());


        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.STUDENT_FOUND,
                result
        );
    }


}
