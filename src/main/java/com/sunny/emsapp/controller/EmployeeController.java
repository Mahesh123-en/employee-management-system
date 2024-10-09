package com.sunny.emsapp.controller;

import com.sunny.emsapp.entity.Employee;
import com.sunny.emsapp.respository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    @GetMapping("/add-employee")
    public String showAddEmployeeForm() {
        return "addEmployee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, Model model) {
        Employee employee = new Employee();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);

        employeeRepository.save(employee);
        getAllEmployees(model);
        return "listEmployees";
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "listEmployees";
    }

    @GetMapping("/update-employee/{id}")
    public String showUpdateEmployeeForm(@PathVariable("id") Integer id, Model model){
        Optional<Employee> employee=employeeRepository.findById(id);
        if(employee.isPresent()){
            model.addAttribute("employee",employee.get());
            return "updateEmployee";
        }
        else{
            return "listEmployees";
        }
    }

    @PostMapping("/update-employee/{id}")
    public String updateEmployee(@PathVariable("id") Integer id,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,Model model){
        Optional<Employee> employeeOptional=employeeRepository.findById(id);
        if(employeeOptional.isPresent()){
            Employee employee=employeeOptional.get();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employeeRepository.save(employee);
            getAllEmployees(model);
        }
        return "listEmployees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, Model model){
        employeeRepository.deleteById(id);
        getAllEmployees(model);
        return "listEmployees";
    }
}
