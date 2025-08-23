package md.streams.service;

import jakarta.annotation.PostConstruct;
import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.DepartmentsService;
import md.streams.interfaces.EmployeeBookService;
import md.streams.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static md.streams.service.EmployeeBookServiceImpl.EMPTY_DOUBLE;

@Service
public class DepartmentServiceImpl implements DepartmentsService {

    private final EmployeeBookService employeeBookService;

    public DepartmentServiceImpl(EmployeeBookService employeeBookService) {
        this.employeeBookService = employeeBookService;
    }

    @PostConstruct
    public void init() {
        employeeBookService.fillEmloyeesList();
    }

    public Employee getDepartmentMaxSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    @Override
    public Double getDepartmentSumSalary(Integer departmentId) throws DepartmentNotProvidedException {

        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    public Employee getDepartmentMinSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public List<Employee> printAllEmployeesDepartment(Integer departmentId) throws DepartmentNotProvidedException {

        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }

    public Map<String, List<Employee>> printAllEmployees() {

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(employee ->
                        "Department: " + employee.getDepartment()
                ));
    }
}
