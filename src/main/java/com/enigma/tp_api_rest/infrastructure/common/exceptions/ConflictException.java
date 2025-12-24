package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends AbstractException{
    public ConflictException() {
        super(HttpStatus.CONFLICT, "Conflict with resources!");
    }

    public ConflictException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.CONFLICT, exceptionEnum.getDescription());
    }

    public ConflictException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.CONFLICT, String.format(exceptionEnum.getDescription(), args));
    }

    public ConflictException(String resourceValue) {
        super(HttpStatus.CONFLICT, String.format("There is conflict with resource %s!", resourceValue));
    }
}
