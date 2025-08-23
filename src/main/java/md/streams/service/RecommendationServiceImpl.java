package md.streams.service;

import md.streams.interfaces.DepartmentsService;
import md.streams.interfaces.EmployeeBookService;
import md.streams.interfaces.RecommendationService;
import md.streams.model.Employee;
import md.streams.model.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    EmployeeBookService employeeBookService;

    @Autowired
    DepartmentsService departmentService;

    /**
     * Генерирует рекомендации для сотрудника на основе статических правил
     */
    public List<Recommendation> generateRecommendations(Integer employeeId) {
        Employee employee = employeeBookService.findEmployeeById(employeeId);
        List<Recommendation> recommendations = new ArrayList<>();

        // Статическое правило 1: Рекомендация повышения зарплаты
        if (employee.getEmployeeSalary() < employeeBookService.getDepartmentAverageSalary(employee.getDepartment())) {
            recommendations.add(createSalaryIncreaseRecommendation(employee));
        }

        // Статическое правило 2: Рекомендация обучения
        recommendations.add(createTrainingRecommendation(employee));

        return recommendations;
    }

    private Recommendation createTrainingRecommendation(Employee employee) {

        return null;
    }

    private Recommendation createSalaryIncreaseRecommendation(Employee employee) {

        return null;
    }
}

