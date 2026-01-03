package com.enigma.tp_api_rest.infrastructure.entrypoint.rest.mappers;

import com.enigma.tp_api_rest.core.domain.category.CreateCategory;
import com.enigma.tp_api_rest.core.domain.category.complete.CategoryComplete;
import com.enigma.tp_api_rest.core.domain.category.simplified.CategorySimplified;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.requests.category.CreateCategoryRequest;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CategoryResponse;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CategorySimplifiedResponse;
import com.enigma.tp_api_rest.infrastructure.entrypoint.rest.responses.category.CreateCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * Ce mapper convertit un dto en modele du domaine
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskMapper.class, MetadataMapper.class})
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    CreateCategory toDomain(CreateCategoryRequest request);
    CategoryResponse toResponse(CategoryComplete category);
    CategorySimplifiedResponse toCategorySimplifiedResponse(CategorySimplified category);
}
