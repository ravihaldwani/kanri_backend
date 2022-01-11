package com.kanrisoft.kanri.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String signingKey;
    private String authoritiesKey;
    private String headerKey;
    private Token token;

    @Getter
    @Setter
    public static class Token {
        private long validity;
        private String prefix;
    }
}