package com.active.authservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Email not found")
public class EmailNotFoundException extends AuthenticationException {

    public EmailNotFoundException() {
        super("User with that email was not found");
    }
}
