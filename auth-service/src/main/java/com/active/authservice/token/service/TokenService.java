package com.active.authservice.token.service;

import com.active.authservice.token.dto.TokenRefreshResponse;
import com.active.authservice.token.exceptions.TokenIssuerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    private final KeyPair keyPair;

    public TokenService(@Value("") String keystorePassword) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        char[] keystorePasswordBytes = keystorePassword.toCharArray();

        KeyStore keyStore = KeyStore.getInstance("JKS");

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream fis = TokenService.class.getResourceAsStream("/keystore.p12")) {
            keyStore.load(fis, keystorePasswordBytes);
        }

        this.keyPair = new KeyPair(
                keyStore.getCertificate("mykey").getPublicKey(),
                (PrivateKey) keyStore.getKey("mykey", keystorePasswordBytes)
        );
    }

    public String generateJWT(String uid) {
        return Jwts.builder()
                .issuer("active")
                .subject(uid)
                .claim("createdOn", new Date())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public String generateRefresh(String uid) {
        return Jwts.builder()
                .issuer("active")
                .subject(uid)
                .expiration(Date.from(Instant.now().plus(365, ChronoUnit.DAYS)))
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public TokenRefreshResponse refreshToken(String refresh) {
        Claims claims = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .decryptWith(keyPair.getPrivate())
                .build()
                .parseSignedClaims(refresh)
                .getPayload();

//        TODO: Add blacklisting

        return TokenRefreshResponse.builder()
                .token(generateJWT(claims.getSubject()))
                .build();
    }

    public String validateToken(String token) throws TokenIssuerException, ExpiredJwtException {
        Claims claims = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .decryptWith(keyPair.getPrivate())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (!claims.getIssuer().equals("active")) {
            throw new TokenIssuerException();
        }

        return claims.getSubject();
    }
}
