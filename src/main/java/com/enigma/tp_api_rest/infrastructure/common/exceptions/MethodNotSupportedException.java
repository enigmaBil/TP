package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotSupportedException extends AbstractException{
    public MethodNotSupportedException() {
        super(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Something wrong. Same like the feature is yet under implementation or under maintenance. Please back soon or contact administrator ^^");
    }

    public MethodNotSupportedException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.METHOD_NOT_ALLOWED, exceptionEnum.getDescription());
    }

    public MethodNotSupportedException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.METHOD_NOT_ALLOWED, String.format(exceptionEnum.getDescription(), args));
    }
}
