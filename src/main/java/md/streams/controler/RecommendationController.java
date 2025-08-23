package md.streams.controler;

import md.streams.interfaces.RecommendationService;
import md.streams.model.Recommendation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/employee/{id}")
    public List<Recommendation> getEmployeeRecommendations(@PathVariable Integer id) {
        return recommendationService.generateRecommendations(id);
    }
}
