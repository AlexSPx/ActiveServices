package com.active.authservice.user.exceptions;

public class EmailAlreadyInUseException extends AuthenticationException {
    public EmailAlreadyInUseException() {
        super("User with that email already exists");
    }
}
