package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends AbstractException{
    public InternalErrorException() {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "It seems that the system is under maintenance. Please back soon or contact administrator to describe your disturbing");
    }

    public InternalErrorException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, exceptionEnum.getDescription());
    }

    public InternalErrorException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format(exceptionEnum.getDescription(), args));
    }

    public InternalErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
