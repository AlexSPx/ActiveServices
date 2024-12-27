package com.active.authservice.token;

import com.active.authservice.token.exceptions.RefreshException;
import com.active.authservice.token.exceptions.TokenException;
import com.active.authservice.token.exceptions.TokenIssuerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public TokenService(@Value("${keystore.password:123456}") String keystorePassword)
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        char[] keystorePasswordBytes = keystorePassword.toCharArray();

        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream fis = TokenService.class.getResourceAsStream("/keystore.p12")) {
            keyStore.load(fis, keystorePasswordBytes);
        }

        this.keyPair = new KeyPair(
                keyStore.getCertificate("mykey").getPublicKey(),
                (PrivateKey) keyStore.getKey("mykey", keystorePasswordBytes)
        );
    }

    public TokenPair generateTokenPair(String uid) {
        return TokenPair.builder()
                .token(generateJWT(uid))
                .refresh(generateRefresh(uid))
                .build();
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

    public String refreshToken(String refresh) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(keyPair.getPublic())
                    .decryptWith(keyPair.getPrivate())
                    .build()
                    .parseSignedClaims(refresh)
                    .getPayload();
        } catch (Exception ignored) {
            throw new RefreshException();
        }

//        TODO: Add blacklisting

        return generateJWT(claims.getSubject());
    }

    public String validateToken(String token) throws TokenIssuerException, ExpiredJwtException {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(keyPair.getPublic())
                    .decryptWith(keyPair.getPrivate())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception ignored) {
            throw new TokenException();
        }

        if (!claims.getIssuer().equals("active")) {
            throw new TokenIssuerException();
        }

        return claims.getSubject();
    }
}
