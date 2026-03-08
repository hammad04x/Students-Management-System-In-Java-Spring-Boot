package com.example.sms.dao;

import com.example.sms.dao.repo.StudentRepo;
import com.example.sms.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class StudentDao extends BasicCURD<Student, String> {

    private StudentRepo studentRepo;

    @Autowired
    public StudentDao(StudentRepo studentRepo) {
        super(studentRepo);
        this.studentRepo = studentRepo;
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepo.findAll(pageable);
    }

    public Boolean existsStudentByEmail(String email) {
        return studentRepo.existsStudentByEmail(email);
    }
    public Boolean existsStudentByPhoneNo(String number) {
        return studentRepo.existsStudentByPhoneNo(number);
    }
}
