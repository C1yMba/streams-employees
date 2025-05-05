package md.streams.service;


import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeBookServiceImpl {
    public static final double EMPTY_DOUBLE = 0.0;

    private List<Employee> employees = new ArrayList<>();

    public void fillEmloyeesArray() {
        employees.add(new Employee("Вася Пупкин Иванович", 1, 10000));
        employees.add(new Employee("Дима Пупкин Петрович", 2, 10000));
        employees.add(new Employee("Вася Непупкин Владимирович", 3, 11000));
        employees.add(new Employee("Кирил Иванов Христофорович", 4, 20000));
        employees.add(new Employee("Петр Первый Иванович", 5, 30000));
        employees.add(new Employee("Андрей Димов Кирилович", 1, 10000));
        employees.add(new Employee("Ирина Пупкина Ивановна", 3, 15000));
        employees.add(new Employee("Мария Ворникова Константиновна", 5, 19800));
        employees.add(new Employee("Анна Ворникова Константиновна", 5, 17000));
        employees.add(new Employee("Кристина Ворникова Константиновна", 5, 15000));
    }

    public Map<String, List<Employee>> printAllEmployees() {
        fillEmloyeesArray();
        return employees.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(employee ->
                        "Department: " + employee.getDepartment()
                ));
    }

    public Employee findEmployeeById(int id) {
        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addNewEmployee(Employee newEmployee) {
        employees.add(newEmployee);
        return true;
    }

    public String removeEmployee(int id) {
        boolean isRemoved = employees.removeIf(employee -> employee != null && employee.getId() == id);

        if (isRemoved) {
            return String.format("Employee with id: %d was removed successfully.", id);
        }
        return String.format("Employee with id: %d was not found.", id);
    }

    public double getAllSalariesSum() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    public Employee getMinSalary() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public Employee getMaxSalary() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public double getSalariesAverageValue() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .mapToDouble(Employee::getEmployeeSalary)
                .average()
                .orElse(EMPTY_DOUBLE);
    }

    public void getEmployeesNames() {
        employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getFullName() != null)
                .map(employee -> "ФИО: " + employee.getFullName())
                .forEach(System.out::println);
    }

    public void indexAllEmployeesSalaries(double percent) {

        for (Employee employee : employees) {
            if (employee != null && employee.getEmployeeSalary() != EMPTY_DOUBLE) {
                employee.setEmployeeSalary(employee.getEmployeeSalary() + (employee.getEmployeeSalary() * (percent / 100)));
            }
        }
    }

    public Employee getDepartmentMinSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        fillEmloyeesArray();

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public Employee getDepartmentMaxSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        fillEmloyeesArray();
        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public double getDepartmentSumSalary(int department) {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    public double getDepartmentAverageSalary(int department) {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getEmployeeSalary)
                .average()
                .orElse(EMPTY_DOUBLE);
    }

    public void indexDepartmentSalaries(int department, double percent) {
        for (Employee employee : employees) {
            if (employee != null && employee.getDepartment() == department) {
                employee.setEmployeeSalary(employee.getEmployeeSalary() + (employee.getEmployeeSalary() * (percent / 100)));
            }
        }
    }

    public List<Employee> printAllEmployeesDepartment(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        fillEmloyeesArray();
        return employees.stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }

    public void getLessSalary(double number) {
        System.out.println("Сотруники с зп поменьше: ");
        employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() < number)
                .map(employee -> "ID сотрудника " + employee.getId() + " ФИО: " + employee.getFullName() + " Зарплата: " + employee.getEmployeeSalary())
                .forEach(System.out::println);
    }

    public void getMoreSalary(double number) {
        System.out.println("Сотрудники с зп побольше:");
        employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() >= number)
                .map(employee -> "ID сотрудника " + employee.getId() + " ФИО: " + employee.getFullName() + " Зарплата: " + employee.getEmployeeSalary())
                .forEach(System.out::println);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
