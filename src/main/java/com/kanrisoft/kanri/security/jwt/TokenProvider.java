package com.kanrisoft.kanri.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements Serializable {

    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;
    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;
    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;
    @Value("${jwt.header.string}")
    public String HEADER_STRING;
    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    private Key getKey() {
        return Keys.hmacShaKeyFor(SIGNING_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsernameFromToken(String authToken) {
        return getClaimFromToken(authToken, Claims::getSubject);
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
        return Jwts.parserBuilder().setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(authToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public UsernamePasswordAuthenticationToken getAuthenticationToken(String authToken, Authentication existingAuth, UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getKey()).build();
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(authToken);
        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream((String[]) claims.get(AUTHORITIES_KEY))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }


}
