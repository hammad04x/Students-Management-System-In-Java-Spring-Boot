package com.example.sms.dao.repo;

import com.example.sms.entity.Student;
import com.example.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

    Page<Student> findAllStudentByStatus(Status status, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " + " (s.firstName LIKE ?1 OR s.lastName LIKE ?1 OR s.email LIKE ?1) " + " AND s.status = ?2 ")
    Page<Student> findAllByFirstNameContainsOrLastNameContainsOrEmailContainsAndStatus(String searchKeyword,
                                                                                       Status status,
                                                                                       Pageable pageable);

    Boolean existsStudentByEmail(String email);

    Boolean existsStudentByPhoneNo(String number);

}
