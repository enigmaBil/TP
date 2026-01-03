package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category;

import com.enigma.tp_api_rest.core.domain.Metadata;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.task.TaskSimplifiedResponse;
import lombok.Builder;

import java.util.Set;

@Builder
public record CategoryResponse(
        String reference,
        String name,
        Set<TaskSimplifiedResponse> tasks,
        Metadata metadata
) {}
