package com.oneandone.stonith.errors;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(Throwable cause, String message, Object... params) {
        super(HttpStatus.UNAUTHORIZED, cause, message, params);
    }

    public UnauthorizedException(String message, Object... params) {
        this(null, message, params);
    }
}
