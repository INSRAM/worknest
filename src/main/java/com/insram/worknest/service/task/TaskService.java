package com.insram.worknest.service.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.dto.task.TaskResponseDTO;
import com.insram.worknest.dto.task.TaskUpdateDto;
import com.insram.worknest.mapper.task.TaskMapper;
import com.insram.worknest.model.entities.task.Task;
import com.insram.worknest.model.entities.task.TaskStatus;
import com.insram.worknest.model.entities.task.comment.Comment;
import com.insram.worknest.model.specification.TaskSpecifications;
import com.insram.worknest.repository.task.TaskRepository;
import com.insram.worknest.repository.task.comment.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final TaskMapper taskMapper;

    // ---------------- Create Task ----------------
    @Transactional
    public TaskResponseDTO createTask(TaskCreateDto dto) {
        log.info("Creating new Task: {}", dto);

        Task task = taskMapper.toEntity(dto);
        log.info("Setting new Task in Entity: {}", task);

        Task saved = taskRepository.save(task);

        log.info("Task saved with ID: {}", saved.getId());
        return taskMapper.toResponseDto(saved);
    }

    // ---------------- List Tasks ----------------
    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> listTasks(
            Optional<String> q,
            Optional<TaskStatus> status,
            Optional<Long> assigneeId,
            Optional<LocalDateTime> dueFrom,
            Optional<LocalDateTime> dueTo,
            Pageable pageable
    ) {
        // Start with the base specification
        Specification<Task> spec = Specification.unrestricted();

        // 1. Check if status is present and reassign spec
        if (status.isPresent()) {
            spec = spec.and(TaskSpecifications.hasStatus(status.get()));
        }

        // 2. Check if assigneeId is present and reassign spec
        if (assigneeId.isPresent()) {
            spec = spec.and(TaskSpecifications.assignedTo(assigneeId.get()));
        }

        // 3. Check if query keyword is present and reassign spec
        if (q.isPresent()) {
            spec = spec.and(TaskSpecifications.titleOrDescContains(q.get()));
        }

        // 4. Handle due date range
        if (dueFrom.isPresent() || dueTo.isPresent()) {
            spec = spec.and(TaskSpecifications.dueBetween(dueFrom.orElse(null), dueTo.orElse(null)));
        }

        // Execute the query with the final, combined specification
        Page<Task> tasks = taskRepository.findAll(spec, pageable);
        return tasks.map(taskMapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {

        // Fetch task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        // Fetch comments with task eagerly to avoid lazy loading issues
        List<Comment> comments = commentRepository.findByTaskIdWithTask(task.getId());

        // Optionally set comments to task (so DTO sees them)
        task.setComments(comments);

        // Safe logging
        comments.forEach(c -> System.out.println(
                "commentId=" + c.getId() +
                        ", taskId=" + c.getTask().getId() +
                        ", text=" + c.getText()
        ));

        // Map task to DTO
        return taskMapper.toResponseDto(task);
    }

//    // ---------------- Get Single Task ----------------
//    @Transactional(readOnly = true)
//    public TaskResponseDTO getTaskById(Long id) {
//        Task task = taskRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
//
//        // Explicitly load comments
//        List<Comment> comments = task.getComments();
//
//        comments.forEach(c -> System.out.println("task in comment = " + c.toString()));
//
//        comments.forEach(c -> System.out.println(
//                "commentId=" + c.getId() + ", taskId=" + c.getTask().getId() +
//                        ", text=" + c.getText()
//        ));
//
//        return taskMapper.toResponseDto(task);
//    }

    // ---------------- Update Single Task ----------------
    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskUpdateDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        taskMapper.updateEntityFromDto(dto, task); // Map non-null fields from DTO to entity
        Task updated = taskRepository.save(task);

        log.info("Task updated: ID={}", updated.getId());
        return taskMapper.toResponseDto(updated);
    }

    // ---------------- Bulk Edit Tasks ----------------
    @Transactional
    public int bulkEdit(
            Optional<String> q,
            Optional<TaskStatus> status,
            Optional<Long> assigneeId,
            Optional<LocalDateTime> dueFrom,
            Optional<LocalDateTime> dueTo,
            TaskUpdateDto updateDto
    ) {
        Specification<Task> spec = Specification.unrestricted();

        // 1. Check if status is present and reassign spec
        if (status.isPresent()) {
            spec = spec.and(TaskSpecifications.hasStatus(status.get()));
        }

        // 2. Check if assigneeId is present and reassign spec
        if (assigneeId.isPresent()) {
            spec = spec.and(TaskSpecifications.assignedTo(assigneeId.get()));
        }

        // 3. Check if query keyword is present and reassign spec
        if (q.isPresent()) {
            spec = spec.and(TaskSpecifications.titleOrDescContains(q.get()));
        }
        if (dueFrom.isPresent() || dueTo.isPresent()) {
            spec = spec.and(TaskSpecifications.dueBetween(dueFrom.orElse(null), dueTo.orElse(null)));
        }

        List<Task> tasks = taskRepository.findAll(spec);
        tasks.forEach(task -> taskMapper.updateEntityFromDto(updateDto, task));

        taskRepository.saveAll(tasks);
        log.info("Bulk edit completed. {} tasks updated.", tasks.size());
        return tasks.size();
    }

    // ---------------- Delete Task ----------------
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
        log.info("Deleted Task with ID={}", id);
    }
}
