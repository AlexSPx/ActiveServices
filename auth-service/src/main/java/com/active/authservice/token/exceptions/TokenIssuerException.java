package com.active.authservice.token.exceptions;

public class TokenIssuerException extends RuntimeException{
    public TokenIssuerException() {
        super("Issuer not accepted");
    }
}
