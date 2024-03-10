package com.active.authservice.user.exceptions;

public class EmailNotFoundException extends AuthenticationException {

    public EmailNotFoundException() {
        super("User with that email was not found");
    }
}
