package com.enigma.tp_api_rest.infrastructure.common.errors;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.AbstractException;
import com.enigma.tp_api_rest.infrastructure.common.exceptions.ValidationException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandling {
    /**
     * Capturer et gérer l'exception de type {@link AbstractException}
     *
     * @param ex l'exception capturée
     * @return response humainement interpretable
     */
    @ExceptionHandler(AbstractException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError customHandleMloException(AbstractException ex) {
        return buildResponseEntity(new ApiError(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("uc_users_phonenumber")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ce numéro de téléphone est déjà utilisé. Veuillez en choisir un autre.");
        }
        if (ex.getMessage().contains("uc_users_email")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cette adresse e-mail est déjà utilisée. Veuillez en choisir une autre.");
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Contrainte de base de données violée.");
    }

    /**
     * Capturer et gérer l'exception de type {@link
     * org.hibernate.exception.ConstraintViolationException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError customHandleConstraintViolationForHibernate(
            org.hibernate.exception.ConstraintViolationException ex) {
        String msg = "Data Constraint violation, " + ex.getConstraintName();
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, msg);
        String messageExcp = ex.getCause().getMessage();
        if (messageExcp != null) {
            String[] subs = StringUtils.substringsBetween(messageExcp, "(", ")");
            List<String> fields =
                    Arrays.stream(subs[0].split(",")).map(String::trim).collect(Collectors.toList());
            List<String> values =
                    Arrays.stream(subs[1].split(",")).map(String::trim).collect(Collectors.toList());
            String messageThruth =
                    String.format(
                            "Data Constraint violation: Détails: La clé « (%s)=(%s) » existe déjà.",
                            String.join(",", fields), String.join(",", values));
            error.setMessage(messageThruth);
        }
        return buildResponseEntity(error);
    }

    /**
     * custom ValidationException's type
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError customHandleValidationException(ValidationException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        if (ex.hasViolationFields()) {
            error.setSubErrors(
                    ex.getFields().stream()
                            .map(
                                    viol ->
                                            new ApiValidationError()
                                                    .setRejectedValue(viol.getRejectedValue())
                                                    .setMessage(viol.getMessage())
                                                    .setField(viol.getFieldName()))
                            .collect(Collectors.toList()));
        }
        return buildResponseEntity(error);
    }

    /**
     * Capturer et gérer l'exception de type {@link ConstraintViolationException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError customHandleNotFound(ConstraintViolationException ex) {
        final String msg = "Constraint violation";
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, msg);
        List<ApiSubError> subErrors =
                ex.getConstraintViolations().stream()
                        .map(
                                cv ->
                                        new ApiValidationError()
                                                .setObject(
                                                        String.format(
                                                                "%s: value '%s' %s",
                                                                cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()))
                                                .setField(cv.getPropertyPath().toString())
                                                .setRejectedValue(cv.getInvalidValue())
                                                .setMessage(cv.getMessage()))
                        .collect(Collectors.toList());
        error.setSubErrors(subErrors);
        return buildResponseEntity(error);
    }

    /**
     * Capturer et gérer les exceptions de type {@link MissingRequestHeaderException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError customMissingRequestHeader(MissingRequestHeaderException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(error);
    }

    public ApiError customValidationError(BindException ex) {
        ApiError error =
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        if (ex.hasErrors()) {
            error.setSubErrors(
                    ex.getFieldErrors().stream()
                            .map(
                                    viol ->
                                            new ApiValidationError()
                                                    .setRejectedValue(viol.getRejectedValue())
                                                    .setMessage(viol.getDefaultMessage())
                                                    .setField(viol.getField()))
                            .collect(Collectors.toList()));
        }
        return buildResponseEntity(error);
    }

    /**
     * Customiser l'exception de type {@link HttpMessageNotReadableException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @NotNull
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        final String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error));
    }

    /**
     * Customiser l'exception de type {@link HttpRequestMethodNotSupportedException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiError handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new ApiError(
                HttpStatus.METHOD_NOT_ALLOWED,
                String.format(
                        "The %s method is not supported. The required are %s",
                        ex.getMethod(), Arrays.toString(ex.getSupportedMethods())));
    }

    /**
     * Customiser les exceptions de type {@link HttpMediaTypeNotSupportedException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @NotNull
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiError handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String message =
                String.format(
                        "The media type %s is not supported.",
                        Objects.requireNonNull(ex.getContentType()).getType());
        ApiError error = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
        return buildResponseEntity(error);
    }

    /**
     * Customiser les exceptions de type {@link MissingPathVariableException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingPathVariable(MissingPathVariableException ex) {
        String message = String.format("The path variable %s is missing.", ex.getVariableName());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);
        return buildResponseEntity(error);
    }

    /**
     * Customiser les exceptions de type {@link HttpMediaTypeNotAcceptableException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiError handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        final String message = "The mediatype is not acceptted.";
        ApiError error = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
        return buildResponseEntity(error);
    }

    /**
     * Customiser les exceptions de type {@link MissingServletRequestParameterException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String message =
                String.format(
                        "The request parameter %s[%s] is missing.",
                        ex.getParameterName(), ex.getParameterType());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);
        return buildResponseEntity(error);
    }

    /**
     * Customiser l'exception non capturée pour non customiser
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleExceptionInternal(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof BindException) {
            return customValidationError((BindException) ex);
        }
        final String message =
                "Internal error. Contact system administrator for more details about this problem";
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message);
        error.setTechnicalMessage(ex.getMessage());
        return buildResponseEntity(error);
    }

    /**
     * Customiser l'exception de type {@link MethodArgumentNotValidException}
     *
     * @param ex l'exception produite
     * @return response humainement interpretable
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ApiSubError> subErrors =
                ex.getBindingResult().getAllErrors().stream()
                        .map(
                                objectError -> {
                                    if (objectError instanceof FieldError fieldError) {
                                        return new ApiValidationError()
                                                .setField(fieldError.getField())
                                                .setRejectedValue(fieldError.getRejectedValue())
                                                .setObject(fieldError.getObjectName())
                                                .setMessage(fieldError.getDefaultMessage());
                                    } else {
                                        String field =
                                                ArrayUtils.isNotEmpty(objectError.getArguments())
                                                        ? ArrayUtils.get(objectError.getArguments(), 1).toString()
                                                        : null;
                                        return new ApiValidationError()
                                                .setMessage(objectError.getDefaultMessage())
                                                .setField(field);
                                    }
                                })
                        .collect(Collectors.toList());
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()).setSubErrors(subErrors);
    }

    /**
     * l'objet {@link ApiError}
     *
     * @param apiError l'error
     * @return response humainement interpretable
     */
    private ApiError buildResponseEntity(ApiError apiError) {
        String sub =
                apiError.getSubErrors() == null
                        ? "NULL"
                        : apiError.getSubErrors().stream()
                        .map(
                                apiSubError -> {
                                    ApiValidationError err = (ApiValidationError) apiSubError;
                                    return String.format(
                                            "FIELD=%s,OBJECT=%s,MESSAGE=%s,REJECTED=%s",
                                            err.getField(),
                                            err.getObject(),
                                            err.getMessage(),
                                            err.getRejectedValue());
                                })
                        .collect(Collectors.joining(";"));
        log.error(
                ErrorUtil.formatCdrLog(
                        String.valueOf(apiError.getStatusCode()), apiError.getMessage(), sub));
        return apiError;
    }
}
