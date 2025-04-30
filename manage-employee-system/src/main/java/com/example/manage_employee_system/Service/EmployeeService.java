package com.example.manage_employee_system.Service;

import com.example.manage_employee_system.Model.EmployeeModel;
import com.example.manage_employee_system.Repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo repo;

    public Page<EmployeeModel> getPaginatedEmployees(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    public Page<EmployeeModel> search(String keyword, int page, int size) {
        return repo.search(keyword, PageRequest.of(page, size));
    }

    public void save(EmployeeModel employee) {
        repo.save(employee);
    }

    public EmployeeModel get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

