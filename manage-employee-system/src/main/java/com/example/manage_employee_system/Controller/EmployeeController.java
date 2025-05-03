package com.example.manage_employee_system.Controller;

import com.example.manage_employee_system.Model.EmployeeModel;
import com.example.manage_employee_system.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/")
    public String list(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "") String keyword,
                       Model model) {
        Page<EmployeeModel> employees = keyword.isEmpty()
                ? service.getPaginatedEmployees(page, 5)
                : service.search(keyword, page, 5);

        model.addAttribute("employees", employees);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employees.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "employee/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        return "employee/form";
    }

    @PostMapping("/new")
    public String save(@Valid @ModelAttribute EmployeeModel employee, BindingResult result) {
        if (result.hasErrors()) return "employee/form";
        service.save(employee);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.get(id));
        return "employee/edit";
    }
    @PostMapping("/edit/")
    public String edit(@Valid @ModelAttribute EmployeeModel employee, BindingResult result) {
        if (result.hasErrors()) return "employee/edit";
        service.save(employee);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
