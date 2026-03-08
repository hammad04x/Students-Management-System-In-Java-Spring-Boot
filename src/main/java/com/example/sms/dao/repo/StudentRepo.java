package com.example.sms.dao.repo;

import com.example.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

    Boolean existsStudentByEmail(String email);
    Boolean existsStudentByPhoneNo(String number);

}
