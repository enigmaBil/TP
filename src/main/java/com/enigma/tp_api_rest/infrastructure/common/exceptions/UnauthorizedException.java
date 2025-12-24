package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends AbstractException{
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "Full authentication is required to get this resource.");
    }

    public UnauthorizedException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.UNAUTHORIZED, exceptionEnum.getDescription());
    }

    public UnauthorizedException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.UNAUTHORIZED, String.format(exceptionEnum.getDescription(), args));
    }
}
