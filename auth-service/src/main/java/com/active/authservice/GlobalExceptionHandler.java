package com.active.authservice;

import com.active.authservice.token.exceptions.RefreshException;
import com.active.authservice.token.exceptions.TokenException;
import com.active.authservice.token.exceptions.TokenIssuerException;
import com.active.authservice.user.exceptions.AuthenticationException;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.GoogleAlreadyConnected;
import com.active.authservice.user.exceptions.GoogleUserDoesNotExistException;
import com.active.authservice.user.exceptions.InvalidGoogleIdTokenException;
import com.active.authservice.user.exceptions.UserNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EmailAlreadyInUseException.class})
    public ResponseEntity<ErrorResponse> emailAlreadyInUseExceptionHandler(EmailAlreadyInUseException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> emailNotFoundExceptionHandler(EmailNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(ExpiredJwtException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationExceptionHandler(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(GoogleAlreadyConnected.class)
    public ResponseEntity<ErrorResponse> googleAlreadyConnectedHandler(GoogleAlreadyConnected e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(GoogleUserDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> googleUserDoesNotExistHandler(GoogleUserDoesNotExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(InvalidGoogleIdTokenException.class)
    public ResponseEntity<ErrorResponse> invalidGoogleIdTokenHandler(InvalidGoogleIdTokenException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundHandler(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> wrongPasswordExceptionHandler(WrongPasswordException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> tokenHandler(TokenException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(RefreshException.class)
    public ResponseEntity<ErrorResponse> refreshTokenHandler(RefreshException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(TokenIssuerException.class)
    public ResponseEntity<ErrorResponse> tokenIssuerHandler(TokenIssuerException e) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
