package com.active.authservice.token.keystore;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;

@Slf4j
@Getter
@Component
public class KeyPairProvider {

    private final KeyPair keyPair;

    public KeyPairProvider(KeyStoreLoader keyStoreLoader,
                           @Value("${keystore.password}") String keystorePassword,
                           @Value("${keystore.alias}") String keystoreAlias) throws Exception {
        log.info("Loading key pair from keystore, alias: {}", keystoreAlias);

        KeyStore keyStore = keyStoreLoader.loadKeyStore(keystorePassword.toCharArray());

        this.keyPair = new KeyPair(
                keyStore.getCertificate(keystoreAlias).getPublicKey(),
                (PrivateKey) keyStore.getKey(keystoreAlias, keystorePassword.toCharArray())
        );
    }
}

