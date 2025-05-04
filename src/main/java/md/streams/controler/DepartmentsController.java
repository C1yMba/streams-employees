package md.streams.controler;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.DepartmentsService;
import md.streams.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {
    private final DepartmentsService departmentsService;

    public DepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }


    @GetMapping(path = "/max-salary")
    public Employee departmentMaxSalary(@RequestParam(required = false) Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.getDepartmentMaxSalary(departmentId);
    }

    @GetMapping(path = "/min-salary")
    public Employee departmentMinSalary(@RequestParam(required = false) Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.getDepartmentMinSalary(departmentId);
    }

    @GetMapping(path = "/all-in-department")
    public List<Employee> departmentEmployees(@RequestParam(required = false) Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.printAllEmployeesDepartment(departmentId);
    }

    @GetMapping(path = "/all")
    public Map<String, List<Employee>> allEmployeesDividedByDepartment() {
        return departmentsService.printAllEmployees();
    }
}
