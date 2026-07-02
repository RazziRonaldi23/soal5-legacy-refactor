package controller;

import entity.Employee;
import service.EmployeeService;

import java.util.List;

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    public List<Employee> findAll(String keyword) throws Exception {
        return employeeService.findAll(keyword);
    }

    public void save(String name, String position, String department, String salaryText) throws Exception {
        employeeService.save(name, position, department, salaryText);
    }

    public void update(int id, String name, String position, String department, String salaryText) throws Exception {
        employeeService.update(id, name, position, department, salaryText);
    }

    public void delete(int id) throws Exception {
        employeeService.delete(id);
    }
}
