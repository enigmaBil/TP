package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.api;

import com.enigma.tp_api_rest.core.usecases.category.CreateCategoryUseCase;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.category.CreateCategoryRequest;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CategoryResponse;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CreateCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
@Tag(name ="Category API", description = "Category API Documentation")
@RequiredArgsConstructor
public class CategoryController {
    private final CreateCategoryUseCase createCategoryUseCase;

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates a new category in the system")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid final CreateCategoryRequest request) {
        log.info("Create category request: {}", request);
        CategoryResponse response = createCategoryUseCase.execute(request);
        HttpStatus status = HttpStatus.CREATED;

        log.info("Created category : {}", response);
        return new ResponseEntity<>(response, status);
    }
}
