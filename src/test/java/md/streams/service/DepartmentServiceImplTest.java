package md.streams.service;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.EmployeeBookService;
import md.streams.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    EmployeeBookService employeeBookService;

    @InjectMocks
    DepartmentServiceImpl departmentsService;

    List<Employee> mockEmployees;

    @BeforeEach
    void setup() {
        mockEmployees = Arrays.asList(
                new Employee("A", 1, 1000),
                new Employee("B", 1, 2000),
                new Employee("C", 2, 3000),
                new Employee("D", 1, 500)
        );
    }

    @Test
    void testGetDepartmentMaxSalary_success() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(mockEmployees);
        Employee max = departmentsService.getDepartmentMaxSalary(1);
        assertNotNull(max);
        assertEquals(2000, max.getEmployeeSalary());
        assertEquals(1, max.getDepartment());
    }

    @Test
    void testGetDepartmentMaxSalary_noMatchingEmployees() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(List.of(
                new Employee("X", 2, 3000)
        ));
        Employee max = departmentsService.getDepartmentMaxSalary(1);
        assertNull(max);
    }

    @Test
    void testGetDepartmentMaxSalary_nullId() {
        assertThrows(DepartmentNotProvidedException.class, () ->
                departmentsService.getDepartmentMaxSalary(null));
    }

    @Test
    void testGetDepartmentMinSalary_success() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(mockEmployees);
        Employee min = departmentsService.getDepartmentMinSalary(1);
        assertNotNull(min);
        assertEquals(500, min.getEmployeeSalary());
    }

    @Test
    void testGetDepartmentMinSalary_ignoreNullsAndZeroSalary() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(List.of(
                new Employee("X", 1, 0.0),
                new Employee("Y", 1, 1500)
        ));
        Employee min = departmentsService.getDepartmentMinSalary(1);
        assertEquals(1500, min.getEmployeeSalary());
    }

    @Test
    void testGetDepartmentMinSalary_nullId() {
        assertThrows(DepartmentNotProvidedException.class, () ->
                departmentsService.getDepartmentMinSalary(null));
    }

    @Test
    void testGetDepartmentSumSalary_success() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(mockEmployees);
        Double sum = departmentsService.getDepartmentSumSalary(1);
        assertEquals(1000 + 2000 + 500, sum);
    }

    @Test
    void testGetDepartmentSumSalary_emptyResult() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(Collections.emptyList());
        Double sum = departmentsService.getDepartmentSumSalary(1);
        assertEquals(0.0, sum);
    }

    @Test
    void testGetDepartmentSumSalary_nullId() {
        assertThrows(DepartmentNotProvidedException.class, () ->
                departmentsService.getDepartmentSumSalary(null));
    }

    @Test
    void testPrintAllEmployeesDepartment_success() throws DepartmentNotProvidedException {
        when(employeeBookService.getEmployees()).thenReturn(mockEmployees);
        List<Employee> list = departmentsService.printAllEmployeesDepartment(1);
        assertEquals(3, list.size());
        assertTrue(list.stream().allMatch(e -> e.getDepartment() == 1));
    }

    @Test
    void testPrintAllEmployeesDepartment_nullId() {
        assertThrows(DepartmentNotProvidedException.class, () ->
                departmentsService.printAllEmployeesDepartment(null));
    }

    @Test
    void testPrintAllEmployees_success() {
        when(employeeBookService.getEmployees()).thenReturn(mockEmployees);
        Map<String, List<Employee>> grouped = departmentsService.printAllEmployees();
        assertEquals(2, grouped.size());
        assertTrue(grouped.containsKey("Department: 1"));
        assertTrue(grouped.containsKey("Department: 2"));
    }
}