package com.enigma.tp_api_rest.infrastructure.database.repositories.impl;

import com.enigma.tp_api_rest.core.domain.category.CreateCategory;
import com.enigma.tp_api_rest.core.domain.category.complete.CategoryComplete;
import com.enigma.tp_api_rest.core.usecases.interactors.CategoryInteractor;
import com.enigma.tp_api_rest.infrastructure.common.exceptions.ConflictException;
import com.enigma.tp_api_rest.infrastructure.common.helpers.ReferenceGenerator;
import com.enigma.tp_api_rest.infrastructure.database.entities.Category;
import com.enigma.tp_api_rest.infrastructure.database.exceptions.CategoryMessage;
import com.enigma.tp_api_rest.infrastructure.database.helpers.ReferencePrefixes;
import com.enigma.tp_api_rest.infrastructure.database.mappers.CategoryEntityMapper;
import com.enigma.tp_api_rest.infrastructure.database.repositories.jpa.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryRepository implements CategoryInteractor {
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public CategoryComplete createCategory(CreateCategory createCategory) {
        // Validation des entrées
        if (categoryJpaRepository.existsByName(createCategory.name())) {
            log.warn("Category with name {} already exists", createCategory.name());
            throw new ConflictException(CategoryMessage.CATEGORY_ALREADY_EXISTS, createCategory.name());
        }
        Category categoryEntity = categoryEntityMapper.toEntity(createCategory);
        Category category = Category.builder()
            .reference(ReferenceGenerator.getInstance().next(ReferencePrefixes.TASK.getPrefix()))
            .name(categoryEntity.getName())
            .createdBy("system")
            .lastModifiedBy("system")
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
        Category savedCategory;
        try{
            savedCategory = categoryJpaRepository.save(category);
            log.info("Saved Category: {}", savedCategory);
        } catch (Exception e){
            log.error("Error while creating Category: {}", e.getMessage());
            throw new RuntimeException("Error while creating Category ", e);
        }
        return categoryEntityMapper.toDomain(savedCategory);

    }
}
