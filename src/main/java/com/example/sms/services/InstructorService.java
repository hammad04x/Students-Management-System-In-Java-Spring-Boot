package com.example.sms.services;

import com.example.sms.dao.InstructorDao;
import com.example.sms.dto.InstructorDTO;
import com.example.sms.dto.ResponseModel;
import com.example.sms.entity.Instructor;
import com.example.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InstructorService {

    @Autowired
    public InstructorDao instructorDao;

    public ResponseModel insertInstructor(InstructorDTO dto) {
        log.info("insertInstructor Method Executed");

        boolean instructorExist = instructorDao.existInstructorByEmail(dto.getEmail());
        if (instructorExist) {
            return ResponseModel.conflict(
                    APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("Email"),
                    null
            );
        }

        instructorExist = instructorDao.existInstructorByPhoneNo(dto.getPhoneNo());
        if (instructorExist) {
            return ResponseModel.conflict(
                    APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("PhoneNo"),
                    null
            );
        }

        log.debug("All Validation Done Ready to Insert new Instructor");
        Instructor instructor = dto.toEntity();
        instructor = instructorDao.save(instructor);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_CREATED,
                InstructorDTO.toDTO(instructor)
        );
    }


}
