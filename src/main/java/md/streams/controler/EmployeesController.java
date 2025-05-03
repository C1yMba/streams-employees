package md.streams.controler;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.EmployeeBookService;
import md.streams.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class EmployeesController {
    private final EmployeeBookService employeeBookService;

    public EmployeesController(EmployeeBookService employeeBookService) {
        this.employeeBookService = employeeBookService;
    }


    @GetMapping(path = "/max-salary")
    public Employee departmentMaxSalary(@RequestParam(required = false) Integer departmentId) throws DepartmentNotProvidedException {
        return employeeBookService.getDepartmentMaxSalary(departmentId);
    }

    @GetMapping(path = "/min-salary")
    public Employee departmentMinSalary(@RequestParam(required = false) Integer departmentId) throws DepartmentNotProvidedException {
        return employeeBookService.getDepartmentMinSalary(departmentId);
    }

    @GetMapping(path = "/all")
    public List<Employee> departmentEmployees(@RequestParam(required = false) Integer departmentId) throws DepartmentNotProvidedException {
       return employeeBookService.printAllEmployeesDepartment(departmentId);
    }

    @GetMapping(path = "/all1")
    public Map<String, List<Employee>> allEmployeesDividedByDepartment(){
       return employeeBookService.printAllEmployees();
    }
}
