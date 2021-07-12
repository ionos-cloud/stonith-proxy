package com.oneandone.stonith.errors;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends Exception {
    private final HttpStatus status;

    public CustomException(HttpStatus status, Throwable cause, String message, Object... params) {
        super(String.format(message, params), cause);
        this.status = status;
    }

    public CustomException(HttpStatus status, String message, Object... params) {
        this(status, null, message, params);
    }

    public CustomException(String message, Object... params) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message, params);
    }

    public CustomException(Throwable cause, String message, Object... params) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, cause, message, params);
    }

    public HttpStatus getStatus() {
        return status;
    }
}

