package com.enigma.tp_api_rest.core.domain.task.simplified;

import java.time.LocalDate;

public record TaskSimplified(
        String reference,
        String name,
        String description,
        LocalDate due
) {}
