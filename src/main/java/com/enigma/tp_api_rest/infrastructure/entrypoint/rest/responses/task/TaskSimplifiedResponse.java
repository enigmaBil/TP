package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskSimplifiedResponse(
        String reference,
        String name,
        String description,
        LocalDate due
) {}
