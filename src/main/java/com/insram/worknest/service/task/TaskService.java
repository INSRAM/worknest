package com.insram.worknest.service.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.mapper.task.TaskMapper;
import com.insram.worknest.model.entities.task.Task;
import com.insram.worknest.repository.task.TaskRepository;
import com.insram.worknest.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

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

            // Setting DTO to Entity using Mapper
            Task newTask = taskMapper.toEntity(dto);

            // Calling Repository to save data in database
            Task savedTask = taskRepository.save(newTask);

            // again changing SavedTask to Dto
            return taskMapper.toDto(savedTask);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
