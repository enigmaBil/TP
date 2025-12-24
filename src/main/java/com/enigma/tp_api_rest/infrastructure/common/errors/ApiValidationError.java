package com.enigma.tp_api_rest.infrastructure.common.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiValidationError extends ApiSubError {
    private  String object;
    private  String field;
    private  Object rejectedValue;
    private  String message;

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
