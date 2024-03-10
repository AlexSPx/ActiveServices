package com.active.authservice.user.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(String message, Throwable clause) {
        super(message, clause);
    }
}
