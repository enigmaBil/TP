package com.enigma.tp_api_rest.infrastructure.database.mappers;

import com.enigma.tp_api_rest.core.domain.category.CreateCategory;
import com.enigma.tp_api_rest.core.domain.category.complete.CategoryComplete;
import com.enigma.tp_api_rest.core.domain.category.simplified.CategorySimplified;
import com.enigma.tp_api_rest.infrastructure.database.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * Ce mapper convertit le modele du domaine en entity jpa et vice versa.
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskEntityMapper.class})
public interface CategoryEntityMapper {
    CategoryEntityMapper INSTANCE = Mappers.getMapper(CategoryEntityMapper.class);

    Category toEntity(CreateCategory category);
    CategoryComplete toDomain(Category category);
    CategorySimplified toCategorySimplifiedDomain(Category category);
}
