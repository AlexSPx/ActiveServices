package com.active.authservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Wrong password")
public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException() {
        super("Wrong password");
    }
}
