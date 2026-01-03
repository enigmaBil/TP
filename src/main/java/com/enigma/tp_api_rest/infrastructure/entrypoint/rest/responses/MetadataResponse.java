package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses;

import java.time.LocalDateTime;

public record MetadataResponse(
        String createdBy,
        String lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {}
