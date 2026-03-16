package com.example.sms.dao;

import com.example.sms.dao.repo.InstructorRepo;
import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class InstructorDao extends BasicCRUD<Instructor, String> {

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    public InstructorDao(InstructorRepo instructorRepo) {
        super(instructorRepo);
        this.instructorRepo = instructorRepo;
    }

    public boolean existInstructorByEmail(String email) {
        return instructorRepo.existsByEmail(email);
    }

    public boolean existInstructorByPhoneNo(String phoneNo) {
        return instructorRepo.existsByPhoneNo(phoneNo);
    }

    public boolean existInstructorByEmailExcludingId(String email, String instructorId) {
        return instructorRepo.existsByEmailAndInstructorIdNot(email, instructorId);
    }

    public boolean existInstructorByPhoneNoExcludingId(String phoneNo, String instructorId) {
        return instructorRepo.existsByPhoneNoAndInstructorIdNot(phoneNo, instructorId);
    }

    public Page<Instructor> findAllByStatus(Status status, Pageable pageable) {
        return instructorRepo.findAllByStatus(status, pageable);
    }

    public Page<Instructor> searchInstructorByStatus(String searchKeyword, Status status, Pageable pageable) {
        return instructorRepo.searchInstructorByStatus("%" + searchKeyword + "%", status, pageable);
    }
}
