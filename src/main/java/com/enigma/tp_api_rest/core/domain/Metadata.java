package com.enigma.tp_api_rest.core.domain;

import java.time.LocalDateTime;

public record Metadata(
        String createdBy,
        String lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {}
