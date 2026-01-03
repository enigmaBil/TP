package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank(message = "Task Name is required")
        String name,
        String description,
        @FutureOrPresent(message = "Due date must be today or in the future")
        LocalDate due
) {}
