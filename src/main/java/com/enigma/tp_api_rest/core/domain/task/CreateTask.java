package com.enigma.tp_api_rest.core.domain.task;

public record CreateTask(
            String name,
            String description,
            String due
) {}
