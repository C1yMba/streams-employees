package md.streams.interfaces;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeBookService {
    void fillEmployeesList();

    Map<String, List<Employee>> printAllEmployees();

    Employee findEmployeeById(int id);

    boolean addNewEmployee(Employee newEmployee);

    String removeEmployee(int id);

    double getAllSalariesSum();

    Employee getMinSalary();

    Employee getMaxSalary();

    double getSalariesAverageValue();

    void getEmployeesNames();

    void indexAllEmployeesSalaries(double percent);

    Employee getDepartmentMinSalary(Integer departmentId) throws DepartmentNotProvidedException;

    Employee getDepartmentMaxSalary(Integer departmentId) throws DepartmentNotProvidedException;

    double getDepartmentSumSalary(int department);

    double getDepartmentAverageSalary(int department);

    void indexDepartmentSalaries(int department, double percent);

    List<Employee> printAllEmployeesDepartment(Integer departmentId) throws DepartmentNotProvidedException;

    void getLessSalary(double number);

    void getMoreSalary(double number);

    List<Employee> getEmployees();
}
