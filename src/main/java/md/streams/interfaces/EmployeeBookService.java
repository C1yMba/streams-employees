package md.streams.interfaces;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeBookService {

    Employee getDepartmentMaxSalary(Integer department) throws DepartmentNotProvidedException;

    Employee getDepartmentMinSalary(Integer department) throws DepartmentNotProvidedException;

    List<Employee> printAllEmployeesDepartment(Integer departmentId) throws DepartmentNotProvidedException;

    Map<String, List<Employee>> printAllEmployees();
}
