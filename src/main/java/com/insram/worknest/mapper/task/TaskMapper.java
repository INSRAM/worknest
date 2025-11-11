package com.insram.worknest.mapper.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.model.entities.task.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskCreateDto dto);
    TaskCreateDto toDto(Task task);

    TaskResponseDTO toResponseDto(Task task);
}
