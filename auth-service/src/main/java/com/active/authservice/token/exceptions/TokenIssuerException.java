package com.active.authservice.token.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Issuer not accepted")
public class TokenIssuerException extends RuntimeException{
    public TokenIssuerException() {
        super("Issuer not accepted");
    }
}
