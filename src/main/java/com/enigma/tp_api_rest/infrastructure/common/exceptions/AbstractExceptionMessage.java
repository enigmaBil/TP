package com.enigma.tp_api_rest.infrastructure.common.exceptions;

/**
 * Interface pour les messages d'exception.
 * * @author emmanuel
 */
public interface AbstractExceptionMessage {
    /**
     * La description de l'erreur (ici message).
     *    *
     *    * <p>La description peut etre une expression regulière
     *    *
     *    * @return le message d'erreur à afficher quand l'exeception sera lévée
     */
    String getDescription();
}
