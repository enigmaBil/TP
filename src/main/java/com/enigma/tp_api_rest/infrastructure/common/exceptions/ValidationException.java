package com.enigma.tp_api_rest.infrastructure.common.exceptions;

import com.enigma.tp_api_rest.infrastructure.common.validator.ViolationErrorField;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends AbstractException {
    private Set<ViolationErrorField> fields;

    public boolean hasViolationFields() {
        return fields != null && fields.size() != 0;
    }

    public ValidationException(AbstractExceptionMessage exceptionEnum) {
        super(HttpStatus.BAD_REQUEST, exceptionEnum.getDescription());
        fields = Set.of();
    }

    public ValidationException(AbstractExceptionMessage exceptionEnum, Object... args) {
        super(HttpStatus.BAD_REQUEST, String.format(exceptionEnum.getDescription(), args));
    }

    public ValidationException(String message, Set<ViolationErrorField> constraintViolations) {
        super(message);
        fields = constraintViolations;
    }

    public ValidationException(Set<ViolationErrorField> constraintViolations) {
        super("constraintViolations");
        fields = constraintViolations;
    }

    public ValidationException(
            Set<ViolationErrorField> constraintViolations,
            AbstractExceptionMessage exceptionEnum,
            Object... args) {
        super(String.format(exceptionEnum.getDescription(), args));
        fields = constraintViolations;
    }

    public ValidationException(
            AbstractExceptionMessage exceptionEnum, Set<ViolationErrorField> constraintViolations) {
        super(exceptionEnum.getDescription());
        fields = constraintViolations;
    }

    public ValidationException(String message) {
        super(message);
    }
}

