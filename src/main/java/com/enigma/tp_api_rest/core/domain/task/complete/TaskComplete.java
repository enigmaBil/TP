package com.enigma.tp_api_rest.core.domain.task.complete;

public record TaskComplete(
    String reference,
    String name,
    String description,
    String dueDate
) {}
