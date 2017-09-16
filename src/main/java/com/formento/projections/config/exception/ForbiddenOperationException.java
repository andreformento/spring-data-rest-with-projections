package com.formento.projections.config.exception;

public class ForbiddenOperationException extends RuntimeException {

    private static final long serialVersionUID = -8530421821406475441L;

    public ForbiddenOperationException(String message) {
        super(message);
    }

}
