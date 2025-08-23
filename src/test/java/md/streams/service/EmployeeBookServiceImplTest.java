package md.streams.service;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.EmployeeBookService;
import md.streams.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeBookServiceImplTest {

    @Autowired
    EmployeeBookService employeeBookService;

    @BeforeEach
    void setUp() {
        employeeBookService.getEmployees().clear();
        employeeBookService.fillEmployeesList();
    }

    @Test
    void testFindEmployeeById() {
        employeeBookService.getEmployees();
        Employee e = employeeBookService.findEmployeeById(303);

        assertNotNull(e);
    }

    @Test
    void testPrintAllEmployees_NotExists() {
        employeeBookService.getEmployees().clear();
        Map<String, List<Employee>> grouped = employeeBookService.printAllEmployees();
        assertTrue(grouped.isEmpty());
    }

    @Test
    void testPrintAllEmployees() {
        Map<String, List<Employee>> grouped = employeeBookService.printAllEmployees();
        assertFalse(grouped.isEmpty());
        assertTrue(grouped.keySet().stream().anyMatch(k -> k.contains("Department: ")));
    }

    @Test
    void testFindEmployeeById_notExists() {
        Employee e = employeeBookService.findEmployeeById(100);
        assertNull(e);
    }

    @Test
    void testAddNewEmployee() {
        Employee newEmp = new Employee("Тест Тестович", 6, 9999);
        boolean added = employeeBookService.addNewEmployee(newEmp);
        assertTrue(added);
        assertEquals(newEmp, employeeBookService.findEmployeeById(newEmp.getId()));
    }

    @Test
    void testRemoveEmployee() {
        String result = employeeBookService.removeEmployee(92);
        assertTrue(result.contains("was removed"));
    }

    @Test
    void testRemoveEmployee_notExists() {
        String result = employeeBookService.removeEmployee(100);
        assertTrue(result.contains("was not found"));
    }

    @Test
    void testGetAllSalariesSum() {
        double sum = employeeBookService.getAllSalariesSum();
        assertTrue(sum > 0);
    }

    @Test
    void testGetAllSalariesSum_notExistEmployee() {
        employeeBookService.getEmployees().clear();
        double sum = employeeBookService.getAllSalariesSum();
        assertTrue(sum == 0);
    }

    @Test
    void testGetMinSalary() {
        Employee min = employeeBookService.getMinSalary();
        assertNotNull(min);
    }

    @Test
    void testGetMinSalaryNotExistsEmployee() {
        employeeBookService.getEmployees().clear();
        Employee min = employeeBookService.getMinSalary();
        assertNull(min);
    }

    @Test
    void testGetMaxSalary() {
        Employee max = employeeBookService.getMaxSalary();
        assertNotNull(max);
    }


    @Test
    void testGetMaxSalaryNotExistsEmployee() {
        employeeBookService.getEmployees().clear();
        Employee max = employeeBookService.getMaxSalary();
        assertNull(max);
    }

    @Test
    void testGetSalariesAverageValue() {
        double avg = employeeBookService.getSalariesAverageValue();
        assertTrue(avg > 0);
    }

    @Test
    void testGetSalariesAverageValueNotExistEmployees() {
        employeeBookService.getEmployees().clear();
        double avg = employeeBookService.getSalariesAverageValue();
        assertTrue(avg == 0.0);
    }

    @Test
    void testIndexAllEmployeesSalaries() {
        double before = employeeBookService.getAllSalariesSum();
        employeeBookService.indexAllEmployeesSalaries(10);
        double after = employeeBookService.getAllSalariesSum();
        assertTrue(after > before);
    }

    @Test
    void testIndexAllEmployeesSalariesPercentNotCorrect() {
        assertThrows(IllegalArgumentException.class, () -> employeeBookService.indexAllEmployeesSalaries(-10));
    }

    @Test
    void testGetDepartmentMinSalary_valid() throws DepartmentNotProvidedException {
        Employee min = employeeBookService.getDepartmentMinSalary(1);
        assertNotNull(min);
    }

    @Test
    void testGetDepartmentMinSalary_invalid() {
        assertThrows(DepartmentNotProvidedException.class, () -> employeeBookService.getDepartmentMinSalary(null));
    }

    @Test
    void testGetDepartmentMaxSalary_valid() throws DepartmentNotProvidedException {
        Employee max = employeeBookService.getDepartmentMaxSalary(1);
        assertNotNull(max);
    }

    @Test
    void testGetDepartmentMaxSalary_invalid() {
        assertThrows(DepartmentNotProvidedException.class, () -> employeeBookService.getDepartmentMaxSalary(null));
    }

    @Test
    void testGetDepartmentSumSalary() {
        double sum = employeeBookService.getDepartmentSumSalary(1);
        assertTrue(sum > 0);
    }

    @Test
    void testGetDepartmentAverageSalary() {
        double avg = employeeBookService.getDepartmentAverageSalary(1);
        assertTrue(avg > 0);
    }

    @Test
    void testIndexDepartmentSalaries() {
        double before = employeeBookService.getDepartmentSumSalary(1);
        employeeBookService.indexDepartmentSalaries(1, 15);
        double after = employeeBookService.getDepartmentSumSalary(1);
        assertTrue(after > before);
    }

    @Test
    void testPrintAllEmployeesDepartment_valid() throws DepartmentNotProvidedException {
        List<Employee> list = employeeBookService.printAllEmployeesDepartment(1);
        assertFalse(list.isEmpty());
    }

    @Test
    void testPrintAllEmployeesDepartment_invalid() {
        assertThrows(DepartmentNotProvidedException.class, () -> employeeBookService.printAllEmployeesDepartment(null));
    }

    @Test
    void testGetLessSalary() {
        assertDoesNotThrow(() -> employeeBookService.getLessSalary(20000));
    }

    @Test
    void testGetMoreSalary() {
        assertDoesNotThrow(() -> employeeBookService.getMoreSalary(10000));
    }
}