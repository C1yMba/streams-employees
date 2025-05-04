package md.streams.service;


import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeBookServiceImpl {
    private Employee[] employees = new Employee[10];

    public static final double EMPTY_DOUBLE = 0.0;

    public void fillEmloyeesArray() {
        employees[0] = new Employee("Вася Пупкин Иванович", 1, 10000);
        employees[1] = new Employee("Дима Пупкин Петрович", 2, 10000);
        employees[2] = new Employee("Вася Непупкин Владимирович", 3, 11000);
        employees[3] = new Employee("Кирил Иванов Христофорович", 4, 20000);
        employees[4] = new Employee("Петр Первый Иванович", 5, 30000);
        employees[5] = new Employee("Андрей Димов Кирилович", 1, 10000);
        employees[6] = new Employee("Ирина Пупкина Ивановна", 3, 15000);
        employees[7] = new Employee("Мария Ворникова Константиновна", 5, 19800);
        employees[8] = new Employee("Анна Ворникова Константиновна", 5, 17000);
        employees[9] = new Employee("Кристина Ворникова Константиновна", 5, 15000);
    }

    public Map<String, List<Employee>> printAllEmployees() {
        fillEmloyeesArray();
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(employee ->
                        "Department: " + employee.getDepartment()
                ));
    }

    public Employee findEmployeeById(int id) {
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addNewEmployee(Employee newEmployee) {
        OptionalInt indexOpt = IntStream.range(0, employees.length)
                .filter(i -> employees[i] == null)
                .findFirst();

        if (indexOpt.isPresent()) {
            employees[indexOpt.getAsInt()] = newEmployee;
            return true;
        }
        return false;
    }

    public String removeEmployee(int id) {

        Employee[] filteredEmployees = Arrays.stream(employees)
                .filter(employee -> employee == null || employee.getId() != id)
                .toArray(Employee[]::new);

        if (filteredEmployees.length < employees.length) {
            employees = filteredEmployees;
            return String.format("Employee with id: %d was removed successfully.", id);
        }

        return String.format("Employee with id: %d was not found.", id);
    }

    public double getAllSalariesSum() {

        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    public Employee getMinSalary() {

        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public Employee getMaxSalary() {

        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public double getSalariesAverageValue() {

        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .mapToDouble(Employee::getEmployeeSalary)
                .average()
                .orElse(EMPTY_DOUBLE);
    }

    public void getEmployeesNames() {
        Arrays.stream(employees)
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

        return Arrays.stream(employees)
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
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public double getDepartmentSumSalary(int department) {

        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    public double getDepartmentAverageSalary(int department) {

        return Arrays.stream(employees)
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
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(e -> e.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }

    public void getLessSalary(double number) {
        System.out.println("Сотруники с зп поменьше: ");
        Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() < number)
                .map(employee -> "ID сотрудника " + employee.getId() + " ФИО: " + employee.getFullName() + " Зарплата: " + employee.getEmployeeSalary())
                .forEach(System.out::println);
    }

    public void getMoreSalary(double number) {
        System.out.println("Сотрудники с зп побольше:");
        Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() >= number)
                .map(employee -> "ID сотрудника " + employee.getId() + " ФИО: " + employee.getFullName() + " Зарплата: " + employee.getEmployeeSalary())
                .forEach(System.out::println);
    }

    public Employee[] getEmployees() {
        return employees;
    }
}
