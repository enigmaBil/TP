package com.enigma.tp_api_rest.infrastructure.database.repositories.impl;

import com.enigma.tp_api_rest.core.domain.task.CreateTask;
import com.enigma.tp_api_rest.core.usecases.interactors.TaskInteractor;
import com.enigma.tp_api_rest.infrastructure.common.helpers.ReferenceGenerator;
import com.enigma.tp_api_rest.infrastructure.database.entities.Task;
import com.enigma.tp_api_rest.infrastructure.database.entities.User;
import com.enigma.tp_api_rest.infrastructure.database.helpers.ReferencePrefixes;
import com.enigma.tp_api_rest.infrastructure.database.mappers.TaskEntityMapper;
import com.enigma.tp_api_rest.infrastructure.database.repositories.jpa.TaskJpaRepository;
import com.enigma.tp_api_rest.infrastructure.database.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskRepository implements TaskInteractor {
    private final TaskJpaRepository taskJpaRepository;
    private final TaskEntityMapper taskEntityMapper;
    private final UserJpaRepository userJpaRepository;


    @Override
    public CreateTask createTask(CreateTask createTask) {
        Task taskEntity = taskEntityMapper.toEntity(createTask);
        User user = userJpaRepository.findByEmail("admin@enigma.com").orElseThrow(
                () -> new RuntimeException("User not found")
        );
        Task task = Task.builder()
                .reference(ReferenceGenerator.getInstance().next(ReferencePrefixes.TASK.getPrefix()))
                .name(taskEntity.getName())
                .description(taskEntity.getDescription())
                .due(taskEntity.getDue())
                .user(user)
                .category(taskEntity.getCategory())
                .createdDate(LocalDateTime.now())
                .createdBy("system")
                .lastModifiedDate(LocalDateTime.now())
                .lastModifiedBy("system")
                .build();
        Task savedTask ;
        try{
            savedTask = taskJpaRepository.save(task);
            log.info("Task saved successfully: {}", savedTask);
        }
        catch (Exception e) {
            log.error("Error saving task: {}", e.getMessage());
            throw  new RuntimeException("Error saving task: " + e.getMessage());
        }
        return taskEntityMapper.toDomain(savedTask);
    }
}
