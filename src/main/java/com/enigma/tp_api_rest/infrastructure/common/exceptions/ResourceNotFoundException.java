package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends AbstractException{
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Could not find resource!");
    }

    public ResourceNotFoundException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.NOT_FOUND, exceptionEnum.getDescription());
    }

    public ResourceNotFoundException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.NOT_FOUND, String.format(exceptionEnum.getDescription(), args));
    }

    public ResourceNotFoundException(String resourceValue) {
        super(HttpStatus.NOT_FOUND, String.format("Could not find resource %s!", resourceValue));
    }
}
