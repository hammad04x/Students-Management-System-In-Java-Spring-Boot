package com.example.sms.dao.repo;

import com.example.sms.entity.Instructor;
import com.example.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, String> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNo(String phoneNo);

    boolean existsByEmailAndInstructorIdNot(String email, String instructorId);

    boolean existsByPhoneNoAndInstructorIdNot(String PhoneNo, String instructorId);

    Page<Instructor> findAllByStatus(Status status, Pageable pageable);

    @Query("SELECT i FROM Instructor i WHERE " +
            "(i.firstName LIKE :search OR i.lastName LIKE :search OR i.email LIKE :search) " +
            "AND i.status = :status")
    Page<Instructor> searchInstructorByStatus(@Param("search") String searchKeyword,
                                              @Param("status") Status status,
                                              Pageable pageable);
}
