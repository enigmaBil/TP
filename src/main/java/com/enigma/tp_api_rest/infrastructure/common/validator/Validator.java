package com.enigma.tp_api_rest.infrastructure.common.validator;

import org.springframework.validation.BindingResult;

public interface Validator<T> {
    /**
     * Verifier que les données fournies par l'utilisateur sont elles valident
     *
     * @return TRUE si données fournies sont valides
     */
    boolean isValid();

    /**
     * Verifier que les données fournies par l'utilisateur sont elles valident et lever une exception
     * si données invalident
     *
     * @return TRUE si données fournies sont valides
     */
    boolean isValidOrThrow();

    /**
     * Recuperer le resultat de la validation dans l'objet {@link BindingResult}
     *
     * @return l'objet {@link BindingResult}
     */
    BindingResult getResult();

    /**
     * Recuperer le nom du validateur.
     *
     * @return nom du validateur
     */
    String getName();

    /**
     * Objet devant être validé.
     *
     * @return l'objet validé
     */
    T getTarget();
}
