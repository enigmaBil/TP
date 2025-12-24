package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends AbstractException{
    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "You are trying to access protected resources");
    }

    public AccessDeniedException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.FORBIDDEN, exceptionEnum.getDescription());
    }

    public AccessDeniedException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.FORBIDDEN, String.format(exceptionEnum.getDescription(), args));
    }
}
