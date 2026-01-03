package com.enigma.tp_api_rest.infrastructure.database.mappers;

import com.enigma.tp_api_rest.core.domain.category.simplified.CategorySimplified;
import com.enigma.tp_api_rest.core.domain.task.CreateTask;
import com.enigma.tp_api_rest.core.domain.task.simplified.TaskSimplified;
import com.enigma.tp_api_rest.infrastructure.database.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * Ce mapper convertit le modele du domaine en entity jpa et vice versa.
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskEntityMapper {
    TaskEntityMapper INSTANCE = Mappers.getMapper(TaskEntityMapper.class);

    Task toEntity(CreateTask task);
    CreateTask toDomain(Task task);
    TaskSimplified toTaskSimplifiedDomain(Task task);
}
