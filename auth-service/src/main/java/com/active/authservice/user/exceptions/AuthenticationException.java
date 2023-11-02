package com.active.authservice.user.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(String message, Throwable clause) {
        super(message, clause);
    }
}
