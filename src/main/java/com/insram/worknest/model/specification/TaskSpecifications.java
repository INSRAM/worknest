package com.insram.worknest.model.specification;

import com.insram.worknest.model.entities.task.Task;
import com.insram.worknest.model.entities.task.TaskStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDateTime;

public final class TaskSpecifications {

    // Specification to filter by first name equality
    public static Specification<Task> hasFirstName(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), title);
    }

    // Specification to filter by status equality
    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    // Specification to filter by CreatedAt greater than
    public static Specification<Task> dateGreaterThanEqual(Instant createdAt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
    }

    public static Specification<Task> assignedTo(Long assignedId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignedId"), assignedId));
    }

    public static Specification<Task> titleOrDescContains(String q) {
        return (root, query, criteriaBuilder) -> {
            // 1. Prepare the search term for LIKE comparison
            String likePattern = "%" + q.toLowerCase() + "%";

            // 2. Create the Title Predicate (title LIKE '%q%' - case-insensitive)
            Predicate titlePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")), // Convert field to lowercase
                    likePattern
            );

            // 3. Create the Description Predicate (description LIKE '%q%' - case-insensitive)
            Predicate descPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")), // Convert field to lowercase
                    likePattern
            );

            // 4. Combine predicates with OR
            return criteriaBuilder.or(titlePredicate, descPredicate);

        };
    }

    public static Specification<Task> dueBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dueDate"), from, to);
    }
}
