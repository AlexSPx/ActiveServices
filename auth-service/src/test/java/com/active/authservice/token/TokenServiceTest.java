package com.active.authservice.token;

import com.active.authservice.token.exceptions.RefreshException;
import com.active.authservice.token.exceptions.TokenException;
import com.active.authservice.token.exceptions.TokenIssuerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TokenService.class, SecretKeyProvider.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Disabled("This test is disabled for now, requires update")
class TokenServiceTest {
    private final TokenService tokenService;

    private KeyPair keyPair;

    @Test
    void generateTokenPair_ShouldReturnTokenPair() {
        String uid = "test-user-id";

        TokenPair tokenPair = tokenService.generateTokenPair(uid);

        assertNotNull(tokenPair);
        assertNotNull(tokenPair.getToken());
        assertNotNull(tokenPair.getRefresh());
    }

    @Test
    void generateTokens_ShouldReturnValidTokenPair() {
        String uid = "test-user-id";

        TokenPair tokenPair = tokenService.refreshToken(uid);

        assertNotNull(tokenPair.getToken());
        assertNotNull(tokenPair.getRefresh());
        Claims claimsRefresh = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(tokenPair.getRefresh())
                .getPayload();

        Claims claimsToken = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(tokenPair.getRefresh())
                .getPayload();

        assertEquals(uid, claimsToken.getSubject());
        assertEquals("active", claimsToken.getIssuer());

        assertEquals(uid, claimsRefresh.getSubject());
        assertEquals("active", claimsRefresh.getIssuer());
    }

    @Test
    void refreshToken_ShouldGenerateNewToken() {
        String uid = "test-user-id";
        String refresh = Jwts.builder()
                .issuer("active")
                .subject(uid)
                .expiration(Date.from(Instant.now().plus(365, ChronoUnit.DAYS)))
                .signWith(keyPair.getPrivate())
                .compact();

        TokenPair tokenPair = tokenService.refreshToken(refresh);

        assertNotNull(tokenPair);
        assertNotNull(tokenPair.getToken());
        assertNotNull(tokenPair.getRefresh());

        Claims claims = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(tokenPair.getToken())
                .getPayload();

        assertEquals(uid, claims.getSubject());
        assertEquals("active", claims.getIssuer());
    }

    @Test
    void refreshToken_InvalidRefreshToken_ShouldThrowException() {
        String invalidRefresh = "invalid-token";

        assertThrows(RefreshException.class, () -> tokenService.refreshToken(invalidRefresh));
    }

    @Test
    void validateToken_ValidToken_ShouldReturnSubject() {
        String uid = "test-user-id";
        String token = Jwts.builder()
                .issuer("active")
                .subject(uid)
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(keyPair.getPrivate())
                .compact();

        String result = tokenService.validateToken(token);

        assertEquals(uid, result);
    }

    @Test
    void validateToken_ExpiredToken_ShouldThrowExpiredJwtException() {
        String token = Jwts.builder()
                .issuer("active")
                .subject("test-user-id")
                .expiration(Date.from(Instant.now().minus(1, ChronoUnit.HOURS)))
                .signWith(keyPair.getPrivate())
                .compact();

        TokenException e = assertThrows(TokenException.class, () -> tokenService.validateToken(token));
        assertInstanceOf(ExpiredJwtException.class, e.getCause());
    }

    @Test
    void validateToken_InvalidIssuer_ShouldThrowTokenIssuerException() {
        String token = Jwts.builder()
                .issuer("invalid-issuer")
                .subject("test-user-id")
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(keyPair.getPrivate())
                .compact();

        assertThrows(TokenIssuerException.class, () -> tokenService.validateToken(token));
    }

    @Test
    void validateToken_InvalidToken_ShouldThrowTokenException() {
        String invalidToken = "invalid-token";

        assertThrows(TokenException.class, () -> tokenService.validateToken(invalidToken));
    }
}
