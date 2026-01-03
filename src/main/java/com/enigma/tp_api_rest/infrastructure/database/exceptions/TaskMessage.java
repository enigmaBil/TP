package com.enigma.tp_api_rest.infrastructure.database.exceptions;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.AbstractExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskMessage implements AbstractExceptionMessage {
    TASK_NOT_FOUND("Task not found with reference: %s"),
    ;

    public final String description;
}
