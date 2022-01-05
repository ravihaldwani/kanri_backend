package com.kanrisoft.kanri.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //getValue() from yml
    @Value("${jwt.header.string}")
    public  String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;
    private final UserDetailsService usrUserDetailsService;
    private final TokenProvider jwtTokenUtil;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, TokenProvider jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
            if(header != null && header.startsWith(TOKEN_PREFIX)){
                    authToken = header.replace(TOKEN_PREFIX, "");
                    try{
                        username = jwtTokenUtil.getUsernameFromToken(authToken);

                    }catch (IllegalArgumentException e){
                        //logger (An error occurred while fetching Username from Token )
                    }
                    catch (ExpiredJwtException e){
                        //logger  (Token has expired )
                    }
                    catch (SignatureException e){
                        //logger (Invalid Credentials)
                    }
            }else{
                    //logger (Could not find bearer string ,header will be ignored)
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails  = usrUserDetailsService.loadUserByUsername(username);

                if(jwtTokenUtil.validateToken(authToken , userDetails)){
                    UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //logger
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request,response);
    }
}
