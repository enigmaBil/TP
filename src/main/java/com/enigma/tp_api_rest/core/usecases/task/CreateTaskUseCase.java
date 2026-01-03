package com.enigma.tp_api_rest.core.usecases.task;

import com.enigma.tp_api_rest.core.domain.task.CreateTask;
import com.enigma.tp_api_rest.core.usecases.AbstractUseCase;
import com.enigma.tp_api_rest.core.usecases.UseCase;
import com.enigma.tp_api_rest.core.usecases.interactors.TaskInteractor;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.mappers.TaskMapper;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.task.CreateTaskRequest;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task.CreateTaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class CreateTaskUseCase implements AbstractUseCase<CreateTaskRequest, CreateTaskResponse> {
    private final TaskMapper taskMapper;
    private final TaskInteractor taskInteractor;

    @Override
    public CreateTaskResponse execute(CreateTaskRequest request) {
        log.info("Executing create task use case with request: {}", request);
        CreateTask createTask = taskMapper.toDomain(request);
        CreateTask createdTask = taskInteractor.createTask(createTask);
        return taskMapper.toResponse(createdTask);
    }
}
