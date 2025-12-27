package com.enigma.tp_api_rest.infrastructure.common.validator;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.ValidationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class AbstractValidator<T> implements Validator<T> {
    @Getter
    private final T target;
    @Getter
    private final String name;
    private boolean validated;
    @Getter private final BindingResult result;

    protected AbstractValidator(T target, String name) {
        this.name = name;
        this.target = target;
        result = new DirectFieldBindingResult(target, name);
    }

    /**
     * Verifier que les données sont valides
     *
     * @return reponse de la validation
     */
    @Override
    public boolean isValid() {
        validateOneTime();
        return !getResult().hasErrors();
    }

    /** Valider l'object {@link T} */
    protected abstract void validate();

    /** Verifier que les données sont valides une fois */
    private void validateOneTime() {
        if (!validated) {
            validated = true;
            validate();
        }
    }


    @Override
    public boolean isValidOrThrow() {
        validateOneTime();
        if (!isValid()) {
            if (log.isDebugEnabled()) {
                logErrors();
            }
            throwError();
        }
        return true;
    }

    /** Léver l'exception avec les champs invalides */
    private void throwError() {
        Set<ViolationErrorField> errors = new HashSet<>();
        getResult()
                .getGlobalErrors()
                .forEach(
                        e ->
                                errors.add(
                                        new ViolationErrorField()
                                                .setMessage(e.getDefaultMessage())
                                                .setFieldName(e.getCode())));
        getResult()
                .getFieldErrors()
                .forEach(
                        e ->
                                errors.add(
                                        new ViolationErrorField()
                                                .setRejectedValue(e.getRejectedValue())
                                                .setFieldName(e.getCode())
                                                .setMessage(e.getDefaultMessage())
                                                .setFieldName(e.getField())));
        throw new ValidationException(
                errors,
                ValidationMessageException.DEFAULT_MESSAGE,
                getResult().getGlobalErrorCount(),
                getResult().getFieldErrorCount());
    }

    /** Logger les messages d'erreur */
    private void logErrors() {
        log.debug(
                "Nombre d'erreurs: {} globale(s), {} sur les champs",
                getResult().getGlobalErrorCount(),
                getResult().getFieldErrorCount());
        if (getResult().hasGlobalErrors()) {
            log.debug("Erreurs globales: ");
            getResult().getGlobalErrors().forEach(e -> log.debug(e.getDefaultMessage()));
        }

        if (getResult().hasFieldErrors()) {
            log.debug("Erreurs sur les champs: ");
            getResult()
                    .getFieldErrors()
                    .forEach(e -> log.debug("{}: {}", e.getField(), e.getDefaultMessage()));
        }
    }
}

