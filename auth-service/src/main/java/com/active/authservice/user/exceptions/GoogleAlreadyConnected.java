package com.active.authservice.user.exceptions;

public class GoogleAlreadyConnected extends RuntimeException {
    public GoogleAlreadyConnected(String message) {
        super(message);
    }

    public GoogleAlreadyConnected(String message, Throwable cause) {
        super(message, cause);
    }
}
