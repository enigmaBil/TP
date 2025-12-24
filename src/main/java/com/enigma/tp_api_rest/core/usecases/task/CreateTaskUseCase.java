package com.enigma.tp_api_rest.core.usecases.task;

import com.enigma.tp_api_rest.core.usecases.AbstractUseCase;
import com.enigma.tp_api_rest.core.usecases.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateTaskUseCase implements AbstractUseCase<CreateTaskRequest, CreateTaskResponse> {
    @Override
    public CreateTaskResponse execute(CreateTaskRequest request) {
        // Implementation logic to create a task goes here
        return new CreateTaskResponse();
    }
}
