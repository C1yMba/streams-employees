package md.streams.service;


import md.streams.model.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class EmployeeBookServiceImpl {
    private Employee[] employees = new Employee[10];

    private static final double EMPTY_DOUBLE = 0.0;

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

    public void printAllEmployees() {
        for (Employee employee : employees) {
            if(employee != null) {
                System.out.println(employee);
            }
        }
    }

    public Employee findEmployeeById(int id){
        return  Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addNewEmployee(Employee newEmployee){
        for (int i = 0; i < employees.length; i++) {
            if(employees[i] == null){
                employees[i] = newEmployee;
                return true;
            }
        }
        return false;
    }

    public String removeEmployee(int id){
        for (int i = 0; i < employees.length; i++) {
            if(employees[i].getId() == id){
                employees[i] = null;
                return String.format("Employee with id: {} was removed successfully.", id);
            }
        }
        return String.format("Employee with id: {} was not found.", id);
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

    public Employee getDepartmentMinSalary(int department) {

        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == department)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    public Employee getDepartmentMaxSalary(int department) {
        return Arrays.stream(employees)
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == department)
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

    public void printAllEmployeesDepartment(int department) {
        for (Employee employee : employees) {
            if (employee != null && employee.getDepartment() == department) {
                System.out.println(employee);
            }
        }
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

}
