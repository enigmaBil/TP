package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.api;

import com.enigma.tp_api_rest.core.domain.task.CreateTask;
import com.enigma.tp_api_rest.core.usecases.task.CreateTaskUseCase;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.task.CreateTaskRequest;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task.CreateTaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task API", description = "API for managing tasks")
public class TaskController {
    private final CreateTaskUseCase createTaskUseCase;

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody @Valid CreateTaskRequest request) {
        log.info("Creating a new task...: {}", request);
        CreateTaskResponse response = createTaskUseCase.execute(request);
        HttpStatus status = HttpStatus.CREATED;

        log.info("Created a new task: {}", response);
        return new ResponseEntity<>(response, status);
    }
}
