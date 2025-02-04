package com.active.authservice.user.exceptions;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("User was not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
