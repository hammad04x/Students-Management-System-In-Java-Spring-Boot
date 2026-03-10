package com.example.sms.dao;

import com.example.sms.dao.repo.InstructorRepo;
import com.example.sms.entity.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
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

}
