package md.streams.service;


import jakarta.annotation.PostConstruct;
import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.EmployeeBookService;
import md.streams.model.Employee;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Сервис для управления книгой сотрудников.
 * Предоставляет функциональность для работы с данными сотрудников,
 * включая поиск, добавление, удаление и расчет статистики по зарплатам.
 */
@Service
@EnableCaching
public class EmployeeBookServiceImpl implements EmployeeBookService {
    public static final double EMPTY_DOUBLE = 0.0;

    private List<Employee> employees = new ArrayList<>();

    @PostConstruct
    public void init() {
        fillEmployeesList();
    }

    /**
     * Заполняет список сотрудников тестовыми данными.
     * Вызывается автоматически после инициализации бина.
     */
    @Override
    public void fillEmployeesList() {
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

    /**
     * Возвращает список сотрудников сгрупированный по дапартаментам.
     */
    @Override
    public Map<String, List<Employee>> printAllEmployees() {
        return employees.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(employee ->
                        "Department: " + employee.getDepartment()
                ));
    }
    /**
     * Возвращает сотрудника из списка сотрудников по id.
     */
    @Override
    public Employee findEmployeeById(int id) {
        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }
    /**
     * Добавляет нового сотрудника в список сотрудников.
     */
    @Override
    @CacheEvict(value = "employees", allEntries = true)
    public boolean addNewEmployee(Employee newEmployee) {
        employees.add(newEmployee);
        return true;
    }

    /**
     * Удаляет сотрудника из списка по id, и возвращает сообщение с подтверждением в случае если сотрудник
     * был найден и убран или же если сотрудника с данным id не существует возвращает соответствующее сообщение.
     */
    @Override
    public String removeEmployee(int id) {
        boolean isRemoved = employees.removeIf(employee -> employee != null && employee.getId() == id);

        if (isRemoved) {
            return String.format("Employee with id: %d was removed successfully.", id);
        }
        return String.format("Employee with id: %d was not found.", id);
    }


    /**
     * Возвращает сумму зарплат всех сотрудников.
     */
    @Override
    public double getAllSalariesSum() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    /**
     * Возвращает сотрудника из списка сотрудников с минимальной зарплатой.
     */
    @Override
    public Employee getMinSalary() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    /**
     * Возвращает сотрудника из списка сотрудников с максимальной зарплатой.
     */
    @Override
    public Employee getMaxSalary() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .max(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    /**
     * Возвращает среднюю зарплату из списка сотрудников.
     */
    @Override
    public double getSalariesAverageValue() {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .mapToDouble(Employee::getEmployeeSalary)
                .average()
                .orElse(EMPTY_DOUBLE);
    }

    /**
     * Печатает в консоли список имен всех сотрудников.
     */
    @Override
    public void getEmployeesNames() {
        employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getFullName() != null)
                .map(employee -> "ФИО: " + employee.getFullName())
                .forEach(System.out::println);
    }

    /**
     * Индексирует зарплату всех сотрудников в случае если был передан отрицательный индекс, то выбрасывает исключение
     * с соответствуюим сообщением.
     */
    @Override
    public void indexAllEmployeesSalaries(double percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("Процент индексации не может быть отрицательным");
        }

        for (Employee employee : employees) {
            if (employee != null && employee.getEmployeeSalary() != EMPTY_DOUBLE) {
                employee.setEmployeeSalary(employee.getEmployeeSalary() + (employee.getEmployeeSalary() * (percent / 100)));
            }
        }
    }

    /**
     * Возвращает сотрудника с минимальной зарплатой в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
    @Override
    public Employee getDepartmentMinSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .min(Comparator.comparingDouble(Employee::getEmployeeSalary))
                .orElse(null);
    }

    /**
     * Возвращает сотрудника с максимальной зарплатой в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
    @Override
    public Employee getDepartmentMaxSalary(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        return employees.stream()
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
    public double getDepartmentSumSalary(int departmentId) {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .mapToDouble(Employee::getEmployeeSalary)
                .sum();
    }

    /**
     * Возвращает среднюю зарплату в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
    @Override
    public double getDepartmentAverageSalary(int departmentId) {

        return employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() != EMPTY_DOUBLE)
                .filter(employee -> employee.getDepartment() == departmentId)
                .mapToDouble(Employee::getEmployeeSalary)
                .average()
                .orElse(EMPTY_DOUBLE);
    }

    /**
     * Индексирует зарплату в департаменте, департамент в свою очередь определяет по переданному departmentId.
     */
    @Override
    public void indexDepartmentSalaries(int departmentId, double percent) {
        for (Employee employee : employees) {
            if (employee != null && employee.getDepartment() == departmentId) {
                employee.setEmployeeSalary(employee.getEmployeeSalary() + (employee.getEmployeeSalary() * (percent / 100)));
            }
        }
    }
    /**
     * Возвращает список всех сторудников департамента, департамент в свою очередь определяет по переданному departmentId.
     */
    @Override
    public List<Employee> printAllEmployeesDepartment(Integer departmentId) throws DepartmentNotProvidedException {
        if (departmentId == null) {
            throw new DepartmentNotProvidedException("Department is not provided");
        }
        return employees.stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getDepartment() == departmentId)
                .collect(Collectors.toList());
    }

    /**
     * Выводит список сотрудников в консоль у которых зарплата меньше переданного number.
     */
    @Override
    public void getLessSalary(double number) {
        System.out.println("Сотруники с зп поменьше: ");
        employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() < number)
                .map(employee -> "ID сотрудника " + employee.getId() + " ФИО: " + employee.getFullName() + " Зарплата: " + employee.getEmployeeSalary())
                .forEach(System.out::println);
    }
    /**
     * Выводит список сотрудников в консоль у которых зарплата больше переданного number.
     */
    @Override
    public void getMoreSalary(double number) {
        System.out.println("Сотрудники с зп побольше:");
        employees.stream()
                .filter(Objects::nonNull)
                .filter(employee -> employee.getEmployeeSalary() >= number)
                .map(employee -> "ID сотрудника " + employee.getId() + " ФИО: " + employee.getFullName() + " Зарплата: " + employee.getEmployeeSalary())
                .forEach(System.out::println);
    }
    /**
     * Возвращает всех сотрудников которые хранятся в репозитории.
     */
    @Override
    @Cacheable("employees")
    public List<Employee> getEmployees() {
        return employees;
    }
}
