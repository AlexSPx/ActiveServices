package com.active.authservice.user.exceptions;

public class InvalidGoogleIdTokenException extends AuthenticationException {
    public InvalidGoogleIdTokenException() {
        super("Invalid Google id token");
    }

}
