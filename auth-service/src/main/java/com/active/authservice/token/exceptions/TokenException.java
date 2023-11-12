package com.active.authservice.token.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Token validation error")
public class TokenException extends RuntimeException {
    public TokenException() {
        super("Token validation error");
    }
}
