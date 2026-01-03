package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedResponse<T> (
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean first,
        boolean last,
        boolean empty
) {
}