package com.enigma.tp_api_rest.core.usecases.interactors;

import com.enigma.tp_api_rest.core.domain.category.CreateCategory;
import com.enigma.tp_api_rest.core.domain.category.complete.CategoryComplete;

public interface CategoryInteractor {
    CategoryComplete createCategory(CreateCategory createCategory);
}
