package com.kanrisoft.kanri.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TokenProviderTest {
    JwtProperties jwtProperties;
    TokenProvider underTest;
    Key key;

    @BeforeEach
    void beforeEach() {
        jwtProperties = mock(JwtProperties.class);
        key = Keys.hmacShaKeyFor("someRandomKeyfasdhflashflasjhdfajsklfha".getBytes(StandardCharsets.UTF_8));
        underTest = new TokenProvider(jwtProperties, key);
    }

    @Test
    void getUsernameFromToken() {
        String expectedUsername = "test@test.com";
        String token = Jwts.builder().setSubject(expectedUsername).signWith(key, SignatureAlgorithm.HS256).compact();

        var username = underTest.getUsernameFromToken(token);

        assertEquals(expectedUsername, username);
    }

    @Test
    void getExpirationDateFromToken() {
        Date expectedDate = Date.from(Instant.now().plus(Duration.ofMinutes(10))
                .truncatedTo(ChronoUnit.SECONDS));
        String token = Jwts.builder().setExpiration(expectedDate).signWith(key, SignatureAlgorithm.HS256).compact();

        var date = underTest.getExpirationDateFromToken(token);

        assertEquals(0, date.compareTo(expectedDate));
    }

    @Test
    void validateToken() {
        String expectedUsername = "test@test.com";
        Date expectedDate = Date.from(Instant.now()
                .plus(Duration.ofMinutes(10)));
        String token = Jwts.builder().setSubject(expectedUsername)
                .setExpiration(expectedDate)
                .signWith(key, SignatureAlgorithm.HS256).compact();
        UserDetails user = Mockito.mock(UserDetails.class);
        when(user.getUsername()).thenReturn(expectedUsername);

        var result = underTest.validateToken(token, user);

        assertTrue(result);
    }

    @Test
    void generateToken() {
        var authentication = new UsernamePasswordAuthenticationToken("test@test.com", "password");
        var tokenMock = mock(JwtProperties.Token.class);
        when(tokenMock.getValidity()).thenReturn(4800L);
        when(jwtProperties.getToken()).thenReturn(tokenMock);
        when(jwtProperties.getAuthoritiesKey()).thenReturn("roles");

        var token = underTest.generateToken(authentication);

        var isTokenValid = Jwts.parserBuilder().setSigningKey(key).build().isSigned(token);
        assertTrue(isTokenValid);
    }

    @Test
    void getAuthenticationToken() {
        var userDetails = mock(UserDetails.class);
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userDetails).getAuthorities();

        var authenticationToken = underTest.getAuthenticationToken(userDetails);

        assertEquals(userDetails, authenticationToken.getPrincipal());
        assertEquals(authorities, authenticationToken.getAuthorities());
        assertEquals("", authenticationToken.getCredentials());
    }
}