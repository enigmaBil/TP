package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends AbstractException{
    public BadRequestException(AbstractExceptionMessage message) {
        super(HttpStatus.BAD_REQUEST, message.getDescription());
    }

    public BadRequestException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.BAD_REQUEST, String.format(exceptionEnum.getDescription(), args));
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
