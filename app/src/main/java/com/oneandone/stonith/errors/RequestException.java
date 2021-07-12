package com.oneandone.stonith.errors;

import org.springframework.http.HttpStatus;

public class RequestException extends CustomException {
    public RequestException(Throwable cause, String message, Object... params) {
        super(HttpStatus.BAD_REQUEST, cause, message, params);
    }

    public RequestException(String message, Object... params) {
        this(null, message, params);
    }
}
