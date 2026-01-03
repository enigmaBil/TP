package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
    @NotBlank(message = "Category Name is mandatory")
    String name
) {}
