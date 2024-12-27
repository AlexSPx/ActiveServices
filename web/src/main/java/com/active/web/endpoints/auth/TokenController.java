package com.active.web.endpoints.auth;

import com.active.authservice.token.TokenService;
import com.active.authservice.token.exceptions.TokenIssuerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String validateToken(@RequestHeader("Authorization") String token) throws TokenIssuerException {
        return tokenService.validateToken(token);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public String refreshToken(@RequestBody String refreshTokenRequest) {
        return tokenService.refreshToken(refreshTokenRequest);
    }
}