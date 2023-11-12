package com.active.authservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid Google id token")
public class InvalidGoogleIdTokenException extends AuthenticationException {
    public InvalidGoogleIdTokenException() {
        super("Invalid Google id token");
    }

}
