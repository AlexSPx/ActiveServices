package com.active.authservice.token;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;

@Getter
@Component
public class KeyPairProvider {
    private final KeyPair keyPair;

    public KeyPairProvider(@Value("${keystore.password:123456}") String keystorePassword) throws Exception {
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

}
