package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task;

import com.enigma.tp_api_rest.core.domain.Metadata;

import java.time.LocalDate;

public record CreateTaskResponse(
         String reference,
         String name,
         String description,
         LocalDate due,
         Metadata metadata
) {}
