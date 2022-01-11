package com.kanrisoft.kanri.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenProvider jwtTokenUtil;
    private final JwtProperties jwtProperties;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, TokenProvider jwtTokenUtil, JwtProperties jwtProperties) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtProperties.getHeaderKey());
        String username = null;
        String authToken = null;
        log.debug("Jwt Filter called");

        if (header != null && header.startsWith(jwtProperties.getToken().getPrefix())) {
            authToken = header.replace(jwtProperties.getToken().getPrefix(), "");

            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("An error occurred while fetching Username from Token");
            } catch (ExpiredJwtException e) {
                log.error("Token has expired");
            }
        }  //logger (Could not find bearer string ,header will be ignored)

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                var authentication = jwtTokenUtil.getAuthenticationToken(userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                //logger
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
