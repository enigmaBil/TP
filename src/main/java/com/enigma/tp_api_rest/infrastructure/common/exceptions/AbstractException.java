package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Abstract class for custom exceptions with HTTP status codes.
 */

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class AbstractException extends RuntimeException {
    private HttpStatus status;
    private String message;

    protected AbstractException(String message) {
        super(message);
        this.message = message;
        status = HttpStatus.BAD_REQUEST;
    }

    protected AbstractException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
