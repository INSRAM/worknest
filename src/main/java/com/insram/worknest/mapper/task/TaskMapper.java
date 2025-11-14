package com.insram.worknest.mapper.task;

import com.insram.worknest.dto.task.TaskCreateDto;
import com.insram.worknest.dto.task.TaskResponseDTO;
import com.insram.worknest.dto.task.TaskUpdateDto;
import com.insram.worknest.model.entities.task.Task;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // --- Create ---
    Task toEntity(TaskCreateDto dto);

    TaskCreateDto toDto(Task task);

    // --- Response ---
    @Mapping(source = "comments", target = "comments")
    TaskResponseDTO toResponseDto(Task task);

    // --- Update (important part) ---
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TaskUpdateDto dto, @MappingTarget Task entity);

}
