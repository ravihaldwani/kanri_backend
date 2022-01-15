package com.kanrisoft.kanri.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements Serializable {

    private final JwtProperties jwtProperties;
    private final Key key;

    public TokenProvider(JwtProperties jwtProperties, Key key) {
        this.jwtProperties = jwtProperties;
        this.key = key;
    }

    public String getUsernameFromToken(String authToken) {
        return getClaimFromToken(authToken, Claims::getSubject);
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(authToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }

    public String generateToken(Authentication authentication) {
        var authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        var expirationDate = Date.from(Instant.now().plusSeconds(jwtProperties.getToken().getValidity()));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(jwtProperties.getAuthoritiesKey(), authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public AbstractAuthenticationToken getAuthenticationToken(UserDetails userDetails) {
        final var authorities = userDetails.getAuthorities();

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
