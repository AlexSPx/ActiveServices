package com.active.authservice.token.keystore;

import java.security.KeyStore;

public interface KeyStoreLoader {
    KeyStore loadKeyStore(char[] password) throws Exception;
}
