package com.active.authservice.token.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Refresh token fail")
public class RefreshException extends RuntimeException{
    public RefreshException() {
        super("Refresh token fail");
    }

}
