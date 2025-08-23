package md.streams.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Recommendation {
    private Long id;
    private Integer employeeId;
    private RecommendationTypeEnum type; // SALARY_INCREASE, PROMOTION, TRAINING
    private String description;
    private Double priority;
    private LocalDateTime createdAt;

    public Recommendation(Long id, Integer employeeId, RecommendationTypeEnum type, String description, Double priority, LocalDateTime createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.type = type;
        this.description = description;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public RecommendationTypeEnum getType() {
        return type;
    }

    public void setType(RecommendationTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(id, that.id) && Objects.equals(employeeId, that.employeeId) && type == that.type && Objects.equals(description, that.description) && Objects.equals(priority, that.priority) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, type, description, priority, createdAt);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                '}';
    }
}
