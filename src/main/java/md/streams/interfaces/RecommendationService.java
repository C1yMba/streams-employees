package md.streams.interfaces;

import md.streams.model.Recommendation;

import java.util.List;

public interface RecommendationService {

    List<Recommendation> generateRecommendations(Integer employeeId);
}
