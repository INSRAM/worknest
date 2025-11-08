package com.insram.worknest.service.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.mapper.task.TaskMapper;
import com.insram.worknest.model.entities.task.Task;
import com.insram.worknest.repository.task.TaskRepository;
import com.insram.worknest.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public TaskCreateDto createTask(TaskCreateDto dto){
        try {

            // --- 1. Log Incoming DTO ---
            log.info("Task creation started. Incoming DTO: {}", dto.toString());

            // Setting DTO to Entity using Mapper
            Task newTask = taskMapper.toEntity(dto);

            // --- 2. Log Mapped Entity Before Persistence ---
            // Note: Assignee (if complex) and ID will likely be null/default here.
            log.info("Mapped new Task Entity before saving: Title='{}', DueDate='{}', Status='{}'",
                    newTask.getTitle(), newTask.getDueDate(), newTask.getStatus());


            // Calling Repository to save data in database
            Task savedTask = taskRepository.save(newTask);

            // --- 3. Log Saved Entity After Persistence ---
            // Check for the generated ID and timestamps.
            log.info("Task successfully saved to repository. Generated ID: {}, CreatedAt: {}",
                    savedTask.getId(), savedTask.getCreatedAt());


            // again changing SavedTask to Dto
            return taskMapper.toDto(savedTask);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
