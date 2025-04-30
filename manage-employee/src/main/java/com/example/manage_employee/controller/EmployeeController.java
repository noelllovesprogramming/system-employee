package com.example.manage_employee.controller;

import com.example.manage_employee.entity.Employee;
import com.example.manage_employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String viewHomePage(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "sortField", required = false) String sortField) {
        List<Employee> listEmployees;
        if (keyword != null && !keyword.isEmpty()) {
            listEmployees = employeeService.searchByNameOrDepartment(keyword);
        } else {
            listEmployees = employeeService.listAll();
        }

        // Sorting by name or department if requested
        if ("name".equalsIgnoreCase(sortField)) {
            listEmployees.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        } else if ("department".equalsIgnoreCase(sortField)) {
            listEmployees.sort((e1, e2) -> e1.getDepartment().compareToIgnoreCase(e2.getDepartment()));
        }

        model.addAttribute("listEmployees", listEmployees);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        return "employee/list";
    }

    @GetMapping("/new")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee/add";
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.get(id);
        if (employee == null) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee);
        return "employee/edit";
    }

    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee) {
        Employee existingEmployee = employeeService.get(id);
        if (existingEmployee == null) {
            return "redirect:/employees";
        }
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setJobTitle(employee.getJobTitle());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setPhoneNumber(employee.getPhoneNumber());
        existingEmployee.setSalary(employee.getSalary());

        employeeService.save(existingEmployee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
