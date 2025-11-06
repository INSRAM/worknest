package com.insram.worknest.model.specification;

import com.insram.worknest.model.entities.task.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public final class TaskSpecifications {

    // Specification to filter by first name equality
    public static Specification<Task> hasFirstName(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), title);
    }

    // Specification to filter by status equality
    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    // Specification to filter by age greater than
    public static Specification<Task> dateGreaterThanEqual(Instant createdAt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
    }
}
