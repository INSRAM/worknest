package com.insram.worknest.controller.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.dto.task.TaskResponseDTO;
import com.insram.worknest.dto.task.TaskUpdateDto;
import com.insram.worknest.model.entities.task.TaskStatus;
import com.insram.worknest.service.task.TaskService;
import com.insram.worknest.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ---------------- Create Task ----------------
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskCreateDto dto) {
        TaskResponseDTO created = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------- List Tasks ----------------
    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> listTasks(
            @RequestParam Optional<String> q,
            @RequestParam Optional<TaskStatus> status,
            @RequestParam Optional<Long> assigneeId,
            @RequestParam Optional<LocalDateTime> dueFrom,
            @RequestParam Optional<LocalDateTime> dueTo,
            Pageable pageable
    ) {
        Page<TaskResponseDTO> tasks = taskService.listTasks(q, status, assigneeId, dueFrom, dueTo, pageable);
        return ResponseEntity.ok(tasks);
    }

    // ---------------- Get Single Task ----------------
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // ---------------- Update Single Task ----------------
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateDto updateDto
    ) {
        TaskResponseDTO updated = taskService.updateTask(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    // ---------------- Bulk Update Tasks ----------------
    @PutMapping("/bulk-edit")
    public ResponseEntity<?> bulkEditTasks(
            @RequestParam Optional<String> q,
            @RequestParam Optional<TaskStatus> status,
            @RequestParam Optional<Long> assigneeId,
            @RequestParam Optional<LocalDateTime> dueFrom,
            @RequestParam Optional<LocalDateTime> dueTo,
            @Valid @RequestBody TaskUpdateDto updateDto
    ) {
        int updatedCount = taskService.bulkEdit(q, status, assigneeId, dueFrom, dueTo, updateDto);
        return ResponseEntity.ok(
                new ApiResponse("Tasks updated successfully", updatedCount)
        );
    }

    // ---------------- Delete Task ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse("Task deleted successfully", 1));
    }


}
