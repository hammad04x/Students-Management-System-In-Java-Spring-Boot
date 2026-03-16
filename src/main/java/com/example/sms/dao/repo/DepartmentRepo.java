package com.example.sms.dao.repo;

import com.example.sms.entity.Department;
import com.example.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepo extends JpaRepository<Department, String> {

    boolean existsByDepartmentName(String departmentName);

    Page<Department> findAllByStatus(Status status, Pageable pageable);

    @Query("SELECT d FROM Department d WHERE " +
            "d.departmentName LIKE :serach " +
            "AND d.status = :status")
    Page<Department> searchDepartmentByStatus(@Param("serach") String serachKeyword,
                                             @Param("status") Status status,
                                             Pageable pageable);

}
