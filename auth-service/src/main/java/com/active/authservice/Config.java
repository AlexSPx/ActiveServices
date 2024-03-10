package com.active.authservice;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public GoogleIdTokenVerifier getGoogleIdTokenVerifier() {
        return new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new GsonFactory())
                .build();
    }
}
