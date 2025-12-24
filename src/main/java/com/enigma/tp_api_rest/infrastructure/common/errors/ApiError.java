package com.enigma.tp_api_rest.infrastructure.common.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiError implements Serializable {
    private int statusCode;
    private String message;
    private String technicalMessage;
    private String correlationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private transient List<ApiSubError> subErrors;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
        correlationId = UUID.randomUUID().toString();
    }

    ApiError(HttpStatus status) {
        this();
        statusCode = status.value();
    }

    ApiError(HttpStatus status, Throwable ex) {
        this(status);
        message = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }

    ApiError(HttpStatus status, String message, String technicalMessage) {
        this(status);
        this.message = message;
        this.technicalMessage = technicalMessage;
    }
}
