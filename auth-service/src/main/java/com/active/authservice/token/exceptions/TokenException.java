package com.active.authservice.token.exceptions;

public class TokenException extends RuntimeException {
    public TokenException(Exception e) {
        super(e);
    }
}
