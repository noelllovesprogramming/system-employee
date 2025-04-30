package com.example.manage_employee_system.Repository;

import com.example.manage_employee_system.Model.EmployeeModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeModel, Long> {
    @Query("SELECT e FROM EmployeeModel e WHERE e.firstName LIKE %:keyword% OR e.lastName LIKE %:keyword%")
    Page<EmployeeModel> search(@Param("keyword") String keyword, Pageable pageable);
}
