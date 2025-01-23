package com.active.web.endpoints;

import com.active.authservice.token.keystore.KeyPairProvider;
import com.active.authservice.token.keystore.KeyStoreLoader;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
@ComponentScan(basePackages = "com.active")
public class TestConfigurations {

    @Bean
    @Primary
    public KeyStoreLoader keyStoreLoader() throws Exception {
        KeyStoreLoader mockKeyStoreLoader = mock(KeyStoreLoader.class);
        KeyStore mockKeyStore = mock(KeyStore.class);

        // Generate an actual RSA key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Use a standard key size
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Create a mock certificate with the real public key
        java.security.cert.Certificate mockCertificate = mock(java.security.cert.Certificate.class);
        when(mockCertificate.getPublicKey()).thenReturn(keyPair.getPublic());

        // Configure the mock keystore loader
        when(mockKeyStoreLoader.loadKeyStore(any(char[].class))).thenReturn(mockKeyStore);

        // Configure the mock keystore behavior
        when(mockKeyStore.getCertificate(anyString())).thenReturn(mockCertificate);
        when(mockKeyStore.getKey(anyString(), any(char[].class))).thenReturn(keyPair.getPrivate());

        return mockKeyStoreLoader;
    }

    @Bean
    @Primary
    public KeyPairProvider keyPairProvider(KeyStoreLoader keyStoreLoader) throws Exception {
        return new KeyPairProvider(keyStoreLoader, "testPassword", "testAlias");
    }
}