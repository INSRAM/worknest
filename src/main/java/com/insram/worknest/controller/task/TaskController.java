package com.insram.worknest.controller.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.service.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskCreateDto> create(@Valid @RequestBody TaskCreateDto dto) { // @Valid triggers DTO validation

        TaskCreateDto createdTask = taskService.createTask(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("list")
    public ResponseEntity<Page<TaskResponseDTO>> listTasks(
            @RequestParam Optional<String> q,
            @RequestParam Optional<TaskStatus> status,
            @RequestParam Optional<Long> assigneeId,
            @RequestParam Optional<LocalDateTime> dueFrom,
            @RequestParam Optional<LocalDateTime> dueTo,
            Pageable pageable) {

        // 1. Delegate the complex logic to the service
        Page<TaskResponseDTO> tasks = taskService.listTasks(q, status, assigneeId, dueFrom, dueTo, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}
