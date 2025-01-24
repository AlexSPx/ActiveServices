package com.active.authservice.token;

import com.active.authservice.token.exceptions.RefreshException;
import com.active.authservice.token.exceptions.TokenException;
import com.active.authservice.token.exceptions.TokenIssuerException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final SecretKeyProvider secretKeyProvider;

    public TokenPair generateTokenPair(String uid) {
        log.debug("Generating token pair for user: {}", uid);

        return TokenPair.builder()
                .token(createJWT(uid, "active", uid, TimeUnit.HOURS.toMillis(1)))
                .refresh(createJWT(uid, "active", uid, TimeUnit.DAYS.toMillis(365)))
                .build();
    }

    public String validateToken(String token) throws TokenIssuerException, ExpiredJwtException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKeyProvider.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!claims.getIssuer().equals("active")) {
                log.info("Token issuer is not active");
                throw new TokenIssuerException();
            }

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.info("Token expired: {}", e.getMessage());
            throw e;
        } catch (SignatureException | InvalidKeyException e) {
            log.info("Token validation failed: {}", e.getMessage());
            throw new TokenException(e);
        }
    }

    public TokenPair refreshToken(String refresh) {
        try {
            Claims claims = getClaims(refresh);

            return generateTokenPair(claims.getSubject());
        } catch (Exception e) {
            throw new RefreshException();
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKeyProvider.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String createJWT(String id, String issuer, String subject, long ttlMillis) {
        log.debug("Creating JWT for subject: {}", subject);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                .id(id)
                .issuedAt(now)
                .subject(subject)
                .issuer(issuer)
                .signWith(secretKeyProvider.getSecretKey())
                .expiration(new Date(nowMillis + ttlMillis))
                .compact();
    }
}