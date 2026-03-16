package com.example.sms.dao;

import com.example.sms.dao.repo.DepartmentRepo;
import com.example.sms.entity.Department;
import com.example.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDao extends BasicCRUD<Department, String> {
    @Autowired
    private DepartmentRepo departmentRepo;


    public DepartmentDao(DepartmentRepo departmentRepo) {
        super(departmentRepo);
        this.departmentRepo = departmentRepo;
    }

    public boolean existsByDepartment(String departmentName) {
        return departmentRepo.existsByDepartmentName(departmentName);
    }

    public Page<Department> findAllByStatus(Status status, Pageable pageable) {
        return departmentRepo.findAllByStatus(status, pageable);
    }

    public Page<Department> searchDepartmentByStatus(String searchKeyword, Status status, Pageable pageable){
        return departmentRepo.searchDepartmentByStatus("%" + searchKeyword + "%", status, pageable);
    }
}
