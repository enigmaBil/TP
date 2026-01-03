package com.enigma.tp_api_rest.infrastructure.database.exceptions;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.AbstractExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryMessage implements AbstractExceptionMessage {
    CATEGORY_NOT_FOUND("Category with id %s not found"),
    CATEGORY_ALREADY_EXISTS("Category with name %s already exists"),
    CATEGORY_HAS_ASSOCIATED_TASKS("Category with id %s has associated tasks and cannot be deleted")
    ;

    public final String description;
}
