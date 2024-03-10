package com.active.authservice.user.exceptions;

public class GoogleUserDoesNotExistException extends RuntimeException {
    public GoogleUserDoesNotExistException() {
        super("Google user does not exist");
    }
}
