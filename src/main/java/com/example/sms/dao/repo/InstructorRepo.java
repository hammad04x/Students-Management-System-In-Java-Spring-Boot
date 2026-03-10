package com.example.sms.dao.repo;

import com.example.sms.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepo extends JpaRepository<Instructor, String> {
}
