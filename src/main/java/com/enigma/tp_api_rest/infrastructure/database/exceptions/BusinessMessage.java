package com.enigma.tp_api_rest.infrastructure.database.exceptions;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.AbstractExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessMessage implements AbstractExceptionMessage {
    INTERNAL_ERROR("Internal error with message:  %s"),
    ACCESS_DENIED("Authentication required"),

    ;

    private final String description;
}
