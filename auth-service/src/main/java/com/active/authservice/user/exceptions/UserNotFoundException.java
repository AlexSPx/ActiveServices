package com.active.authservice.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User was not found")
public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("User was not found");
    }
}
