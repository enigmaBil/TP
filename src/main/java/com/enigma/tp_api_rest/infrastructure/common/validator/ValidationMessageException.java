package com.enigma.tp_api_rest.infrastructure.common.validator;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.AbstractExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ValidationMessageException implements AbstractExceptionMessage {
    DEFAULT_MESSAGE("Errors count: %d global(s) and %d on the fields");

    private String description;

    /**
     * Recuperer la description du message d'erreur de l'exception
     *
     * @return le message à afficher à l'utilisateur
     */
    @Override
    public String getDescription() {
        return description;
    }
}
