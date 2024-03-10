package com.active.authservice.token.exceptions;

public class RefreshException extends RuntimeException {
    public RefreshException() {
        super("Refresh token fail");
    }
}
