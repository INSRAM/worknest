package com.insram.worknest.mapper.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.dto.task.TaskResponseDTO;
import com.insram.worknest.dto.task.TaskUpdateDto;
import com.insram.worknest.model.entities.task.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // --- Create ---
    Task toEntity(TaskCreateDto dto);

    TaskCreateDto toDto(Task task);

    // --- Response ---
    TaskResponseDTO toResponseDto(Task task);

    // --- Update (important part) ---
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TaskUpdateDto dto, @MappingTarget Task entity);

}
