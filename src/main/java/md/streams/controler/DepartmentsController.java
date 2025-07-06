package md.streams.controler;

import md.streams.exceptions.DepartmentNotProvidedException;
import md.streams.interfaces.DepartmentsService;
import md.streams.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentsController {
    private final DepartmentsService departmentsService;

    public DepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @GetMapping(path = "/{id}/salary/sum")
    public Double departmentSumSalary(@PathVariable("id") Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.getDepartmentSumSalary(departmentId);
    }


    @GetMapping(path = "/{id}/salary/max")
    public Employee departmentMaxSalary(@PathVariable("id") Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.getDepartmentMaxSalary(departmentId);
    }

    @GetMapping(path = "/{id}/salary/min")
    public Employee departmentMinSalary(@PathVariable("id") Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.getDepartmentMinSalary(departmentId);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> departmentEmployees(@PathVariable("id") Integer departmentId) throws DepartmentNotProvidedException {
        return departmentsService.printAllEmployeesDepartment(departmentId);
    }

    @GetMapping(path = "/employees")
    public Map<String, List<Employee>> allEmployeesDividedByDepartment() {
        return departmentsService.printAllEmployees();
    }
}
