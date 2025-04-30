package com.example.manage_employee.service;

import com.example.manage_employee.entity.Employee;
import com.example.manage_employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee get(Long id) {
        Optional<Employee> result = employeeRepository.findById(id);
        return result.orElse(null);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> searchByNameOrDepartment(String keyword) {
        return employeeRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(keyword, keyword);
    }
}
