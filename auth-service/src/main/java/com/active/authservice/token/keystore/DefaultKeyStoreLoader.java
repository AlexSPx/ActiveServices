package com.active.authservice.token.keystore;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;

@Component
public class DefaultKeyStoreLoader implements KeyStoreLoader {
    @Override
    public KeyStore loadKeyStore(char[] password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (InputStream fis = getClass().getResourceAsStream("/keystore.p12")) {
            if (fis == null) {
                throw new IllegalArgumentException("Keystore file not found");
            }
            keyStore.load(fis, password);
        }

        return keyStore;
    }
}
