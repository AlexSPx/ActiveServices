package com.active.authservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already in use")
public class EmailAlreadyInUseException extends AuthenticationException {
    public EmailAlreadyInUseException() {
        super("User with that email already exists");
    }
}
