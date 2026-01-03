package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.mappers;

import com.enigma.tp_api_rest.core.domain.task.CreateTask;
import com.enigma.tp_api_rest.core.domain.task.simplified.TaskSimplified;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.task.CreateTaskRequest;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task.CreateTaskResponse;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task.TaskSimplifiedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * Ce mapper convertit les dtos en model du domaine et inversement.
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MetadataMapper.class})
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    CreateTask toDomain(CreateTaskRequest request);
    CreateTaskResponse toResponse(CreateTask task);
    TaskSimplifiedResponse toTaskSimplifiedResponse(TaskSimplified task);
}
