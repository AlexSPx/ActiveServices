package com.active.authservice.token;

import com.active.authservice.token.dto.RefreshTokenRequest;
import com.active.authservice.token.dto.TokenRefreshResponse;
import com.active.authservice.token.exceptions.TokenIssuerException;
import com.active.authservice.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public TokenRefreshResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return tokenService.refreshToken(refreshTokenRequest.getRefreshToken());
    }
}
