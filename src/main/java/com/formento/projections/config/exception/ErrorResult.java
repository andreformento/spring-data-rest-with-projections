package com.formento.projections.config.exception;

import java.io.Serializable;

class ErrorResult implements Serializable {

    private final String message;

    public ErrorResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
