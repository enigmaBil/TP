package com.enigma.tp_api_rest.core.usecases.category;

import com.enigma.tp_api_rest.core.domain.category.CreateCategory;
import com.enigma.tp_api_rest.core.domain.category.complete.CategoryComplete;
import com.enigma.tp_api_rest.core.usecases.AbstractUseCase;
import com.enigma.tp_api_rest.core.usecases.UseCase;
import com.enigma.tp_api_rest.core.usecases.interactors.CategoryInteractor;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.mappers.CategoryMapper;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.category.CreateCategoryRequest;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CategoryResponse;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CreateCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class CreateCategoryUseCase implements AbstractUseCase<CreateCategoryRequest, CategoryResponse> {
    private final CategoryInteractor categoryInteractor;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse execute(CreateCategoryRequest request) {
        log.info("Executing create category use case with request: {}", request);
        CreateCategory createCategory = categoryMapper.toDomain(request);
        CategoryComplete createdCategory = categoryInteractor.createCategory(createCategory);
        return categoryMapper.toResponse(createdCategory);
    }
}
