package com.active.authservice.token;

import com.active.authservice.token.exceptions.RefreshException;
import com.active.authservice.token.exceptions.TokenException;
import com.active.authservice.token.exceptions.TokenIssuerException;
import com.active.authservice.token.keystore.KeyPairProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
    private final KeyPairProvider keyPairProvider;

    public TokenPair generateTokenPair(String uid) {
        log.debug("Generating token pair for user: {}", uid);

        return TokenPair.builder()
                .token(generateJWT(uid))
                .refresh(generateRefresh(uid))
                .build();
    }

    public String generateJWT(String uid) {
        log.debug("Generating JWT for user: {}", uid);

        return Jwts.builder()
                .issuer("active")
                .subject(uid)
                .claim("createdOn", new Date())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(keyPairProvider.getKeyPair().getPrivate())
                .compact();
    }

    public String generateRefresh(String uid) {
        log.debug("Generating refresh token for user: {}", uid);

        return Jwts.builder()
                .issuer("active")
                .subject(uid)
                .expiration(Date.from(Instant.now().plus(365, ChronoUnit.DAYS)))
                .signWith(keyPairProvider.getKeyPair().getPrivate())
                .compact();
    }

    public TokenPair refreshToken(String refresh) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(keyPairProvider.getKeyPair().getPublic())
                    .decryptWith(keyPairProvider.getKeyPair().getPrivate())
                    .build()
                    .parseSignedClaims(refresh)
                    .getPayload();
        } catch (Exception ignored) {
            throw new RefreshException();
        }

//        TODO: Add blacklisting

        return generateTokenPair(claims.getSubject());
    }

    public String validateToken(String token) throws TokenIssuerException, ExpiredJwtException {
        Claims claims;

        log.info("Validating token: {}", token);
        try {
            claims = Jwts.parser()
                    .verifyWith(keyPairProvider.getKeyPair().getPublic())
                    .decryptWith(keyPairProvider.getKeyPair().getPrivate())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.info("Token validation failed: {}", e.getMessage());
            throw new TokenException(e);
        }

        if (!claims.getIssuer().equals("active")) {
            log.info("Token issuer is not active");
            throw new TokenIssuerException();
        }

        return claims.getSubject();
    }
}
