package com.enigma.tp_api_rest.infrastructure.common.validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public final class ViolationErrorField {
    private String message;
    private String fieldName;
    private Object rejectedValue;
}