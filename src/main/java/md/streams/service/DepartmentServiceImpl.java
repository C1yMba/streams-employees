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
    /**
     * Заполняет список сотрудников тестовыми данными.
     * Вызывается автоматически после инициализации бина.
     */
    @PostConstruct
    public void init() {
        employeeBookService.fillEmployeesList();
    }
    /**
     * Возвращает сотрудника с максимальной зарплатой в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
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
    /**
     * Возвращает сумму всех зарплат в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
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
    /**
     * Возвращает сотрудника с минимальной зарплатой в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
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
    /**
     * Возвращает список всех сотрудников департамента, департамент в свою очередь определяет по переданному departmentId.
     */
    public List<Employee> printAllEmployeesDepartment(Integer departmentId) throws DepartmentNotProvidedException {

        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }
    /**
     * Возвращает списко всех сотрудников сгрупированных по департаменту.
     */
    public Map<String, List<Employee>> printAllEmployees() {

        return employeeBookService.getEmployees().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(employee ->
                        "Department: " + employee.getDepartment()
                ));
    }
}
