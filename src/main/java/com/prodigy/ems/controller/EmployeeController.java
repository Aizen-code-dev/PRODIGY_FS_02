package com.prodigy.ems.controller;

import com.prodigy.ems.model.Employee;
import com.prodigy.ems.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    // LIST EMPLOYEES
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-list";
    }

    // SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Employee employee) {
        return "employee-add";
    }

    // HANDLE ADD FORM
    @PostMapping("/add")
    public String addEmployee(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employee-add";
        }
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "employee-edit";
    }

    // HANDLE EDIT FORM
    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Long id,
                               @Valid Employee employee,
                               BindingResult result) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "employee-edit";
        }
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }

    // DELETE EMPLOYEE
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
        return "redirect:/admin/employees";
    }
}
