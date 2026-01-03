package com.enigma.tp_api_rest.core.domain.category.complete;

import com.enigma.tp_api_rest.core.domain.task.simplified.TaskSimplified;
import com.enigma.tp_api_rest.core.domain.Metadata;

import java.util.Set;

public record CategoryComplete(
        String reference,
        String name,
        Set<TaskSimplified> tasks,
        Metadata metadata
) {}
