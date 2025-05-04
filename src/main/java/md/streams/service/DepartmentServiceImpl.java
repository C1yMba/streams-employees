package md.streams.service;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.DepartmentsService;
import md.streams.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static md.streams.service.EmployeeBookServiceImpl.EMPTY_DOUBLE;

@Service
public class DepartmentServiceImpl implements DepartmentsService {

    private final EmployeeBookServiceImpl employeeBookService;

    public DepartmentServiceImpl(EmployeeBookServiceImpl employeeBookService) {
        this.employeeBookService = employeeBookService;
    }

    public Employee getDepartmentMaxSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        employeeBookService.fillEmloyeesArray();
        return Arrays.stream(employeeBookService.getEmployees())
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public Employee getDepartmentMinSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        employeeBookService.fillEmloyeesArray();

        return Arrays.stream(employeeBookService.getEmployees())
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
        employeeBookService.fillEmloyeesArray();
        return Arrays.stream(employeeBookService.getEmployees())
                .filter(Objects::nonNull)
                .filter(e -> e.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }

    public Map<String, List<Employee>> printAllEmployees() {
        employeeBookService.fillEmloyeesArray();
        return Arrays.stream(employeeBookService.getEmployees())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(employee ->
                        "Department: " + employee.getDepartment()
                ));
    }
}
