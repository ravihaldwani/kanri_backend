package com.kanrisoft.kanri.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class SigningKey {

    @Bean
    Key getSigningKey(@Value("${jwt.signing-key}") String signingKey) {
        return Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    }
}
