package com.active.web.endpoints.auth;

import com.active.authservice.token.TokenPair;
import com.active.authservice.token.TokenService;
import com.active.authservice.token.exceptions.TokenIssuerException;
import com.active.web.dto.auth.RefreshToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/token")
@RequiredArgsConstructor
@Validated
public class TokenController {
    private final TokenService tokenService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String validateToken(@RequestHeader("Authorization") String token) throws TokenIssuerException {
        return tokenService.validateToken(token);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenPair> refreshToken(@Valid @RequestBody RefreshToken refreshTokenRequest) {
        return ResponseEntity.ok(tokenService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }
}