package com.prodigy.ems.controller;

import com.prodigy.ems.model.Employee;
import com.prodigy.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final EmployeeRepository employeeRepository;

    // DASHBOARD REDIRECTION BASED ON ROLE
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return isAdmin ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
    }

    // ADMIN DASHBOARD
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Authentication auth, Model model) {
        model.addAttribute("username", auth.getName());

        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);

        return "admin-dashboard";
    }

    // USER DASHBOARD
    @GetMapping("/user/dashboard")
    public String userDashboard(Authentication auth, Model model) {
        model.addAttribute("username", auth.getName());

        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);

        return "user-dashboard";
    }
}
