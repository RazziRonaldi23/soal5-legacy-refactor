package service;

import entity.Employee;
import repository.EmployeeRepository;
import utils.ValidationUtil;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService() {
        this.employeeRepository = new EmployeeRepository();
    }

    public List<Employee> findAll(String keyword) throws Exception {
        return employeeRepository.findAll(keyword == null ? "" : keyword);
    }

    public void save(String name, String position, String department, String salaryText) throws Exception {
        Employee employee = buildEmployee(0, name, position, department, salaryText);
        employeeRepository.save(employee);
    }

    public void update(int id, String name, String position, String department, String salaryText) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Pilih data yang ingin diubah.");
        }

        Employee employee = buildEmployee(id, name, position, department, salaryText);
        employeeRepository.update(employee);
    }

    public void delete(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Pilih data yang ingin dihapus.");
        }

        employeeRepository.delete(id);
    }

    private Employee buildEmployee(int id, String name, String position, String department, String salaryText) {
        if (
                ValidationUtil.isEmpty(name) ||
                ValidationUtil.isEmpty(position) ||
                ValidationUtil.isEmpty(department) ||
                ValidationUtil.isEmpty(salaryText)
        ) {
            throw new IllegalArgumentException("Semua input wajib diisi.");
        }

        double salary;

        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Gaji harus berupa angka.");
        }

        if (salary < 0) {
            throw new IllegalArgumentException("Gaji tidak boleh negatif.");
        }

        return new Employee(id, name.trim(), position.trim(), department.trim(), salary);
    }
}
