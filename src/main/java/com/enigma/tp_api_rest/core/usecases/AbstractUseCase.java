package com.enigma.tp_api_rest.core.usecases;

public interface AbstractUseCase<I, O> {
    default  O execute(I request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default O execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default  void executeSilent(I request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default  void executeSilent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
