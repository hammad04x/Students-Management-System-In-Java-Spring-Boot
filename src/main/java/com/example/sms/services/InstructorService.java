package com.example.sms.services;

import com.example.sms.dao.InstructorDao;
import com.example.sms.dto.InstructorDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Status;
import com.example.sms.exception.DuplicateExceptionResource;
import com.example.sms.exception.NotFoundExceptionResource;
import com.example.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InstructorService {

    @Autowired
    public InstructorDao instructorDao;

    public ResponseModel insertInstructor(InstructorDTO dto) {
        log.info("insertInstructor Method Executed");

        boolean instructorExist = instructorDao.existInstructorByEmail(dto.getEmail());
        if (instructorExist) {
            throw new DuplicateExceptionResource(APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("Email"));
        }

        instructorExist = instructorDao.existInstructorByPhoneNo(dto.getPhoneNo());
        if (instructorExist) {
            throw new DuplicateExceptionResource(APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("phoneNo"));
        }

        log.debug("All Validation Done Ready to Insert new Instructor");
        Instructor instructor = dto.toEntity();
        instructor = instructorDao.save(instructor);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_CREATED,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel getAllInstructors(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Instructor> instructors = instructorDao.findAllByStatus(Status.ACTIVE, pageable);
        log.info("Before list ");
        System.out.println(instructors);
        List<InstructorDTO> dtos = instructors.getContent()
                .stream()
                .map(InstructorDTO::toDTO)
                .toList();
        log.info("After list ");
        System.out.println(dtos);

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", instructors.getTotalElements());
        pageResult.put("pageCount", instructors.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_FOUND,
                result
        );
    }

    public ResponseModel getInstructorById(String instructorId) {

        Instructor instructor = instructorDao.findById(instructorId);
        if (instructor == null) {
            throw new NotFoundExceptionResource(APIMessage.INSTRUCTOR_NOT_FOUND);
        }

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_FOUND,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel updateInstructorById(String instructorId, InstructorDTO dto) {
        Instructor instructor = instructorDao.findById(instructorId);
        if (instructor == null) {
            return ResponseModel.not_found(
                    APIMessage.INSTRUCTOR_NOT_FOUND,
                    null
            );
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(instructor.getEmail())) {
            boolean instructorExist = instructorDao.existInstructorByEmailExcludingId(dto.getEmail(), instructorId);
            if (instructorExist) {
                return ResponseModel.conflict(
                        APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("email"),
                        null
                );
            }
        }
        if (dto.getPhoneNo() != null && !dto.getPhoneNo().equals(instructor.getPhoneNo())) {
            boolean instructorExist = instructorDao.existInstructorByPhoneNoExcludingId(dto.getPhoneNo(), instructorId);
            if (instructorExist) {
                return ResponseModel.conflict(
                        APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("phoneNo"),
                        null
                );
            }
        }

        instructor = dto.toUpdateEntity(instructor);
        instructor = instructorDao.save(instructor);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_UPDATED,
                InstructorDTO.toDTO(instructor)
        );

    }

    public ResponseModel deleteInstructorById(String instructorId) {
        Instructor instructor = instructorDao.findById(instructorId);
        if (instructor == null) {
            return ResponseModel.not_found(
                    APIMessage.INSTRUCTOR_NOT_FOUND,
                    null
            );
        }

        instructor.setStatus(Status.DELETED);
        instructor = instructorDao.save(instructor);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_DELETED,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel searchInstructor(String keyword, int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        Page<Instructor> instructors = instructorDao.searchInstructorByStatus(keyword, Status.ACTIVE, pageable);

        List<InstructorDTO> dtos = instructors.getContent()
                .stream()
                .map(InstructorDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", instructors.getTotalElements());
        pageResult.put("totalPages", instructors.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_FOUND,
                result
        );

    }

}
