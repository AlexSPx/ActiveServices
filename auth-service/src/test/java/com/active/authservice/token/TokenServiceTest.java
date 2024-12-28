package com.active.authservice.token;

import com.active.authservice.token.exceptions.RefreshException;
import com.active.authservice.token.exceptions.TokenException;
import com.active.authservice.token.exceptions.TokenIssuerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TokenService.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TokenServiceTest {

    @MockBean
    private KeyPairProvider keyPairProvider;


    private final TokenService tokenService;

    private KeyPair keyPair;

    @BeforeEach
    @SuppressWarnings("deprecation")
    void setUp() {
        keyPair = Keys.keyPairFor(io.jsonwebtoken.SignatureAlgorithm.RS256);
        when(keyPairProvider.getKeyPair()).thenReturn(keyPair);
    }

    @Test
    void generateTokenPair_ShouldReturnTokenPair() {
        String uid = "test-user-id";

        TokenPair tokenPair = tokenService.generateTokenPair(uid);

        assertNotNull(tokenPair);
        assertNotNull(tokenPair.getToken());
        assertNotNull(tokenPair.getRefresh());
    }

    @Test
    void generateJWT_ShouldReturnValidToken() {
        String uid = "test-user-id";

        String token = tokenService.generateJWT(uid);

        assertNotNull(token);
        Claims claims = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals(uid, claims.getSubject());
        assertEquals("active", claims.getIssuer());
    }

    @Test
    void generateRefresh_ShouldReturnValidRefreshToken() {
        String uid = "test-user-id";

        String refresh = tokenService.generateRefresh(uid);

        assertNotNull(refresh);
        Claims claims = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(refresh)
                .getPayload();

        assertEquals(uid, claims.getSubject());
        assertEquals("active", claims.getIssuer());
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

        String newToken = tokenService.refreshToken(refresh);

        assertNotNull(newToken);
        Claims claims = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(newToken)
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
        assertTrue(e.getCause() instanceof ExpiredJwtException);
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
