package com.active.authservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Google user does not exist")
public class GoogleUserDoesNotExistException extends Exception {
    public GoogleUserDoesNotExistException() {
        super("Google user does not exist");
    }

}
