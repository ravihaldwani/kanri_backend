package com.kanrisoft.kanri.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.http.HttpServletResponse;

@Order(1)
@ActiveProfiles("test")
@Configuration
@EnableWebSecurity
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        AuthenticationEntryPoint unauthorizedEntryPoint = (request, response, authenticationException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error : Unauthorized");

        httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/register").permitAll()
                .antMatchers("/api/v1/user/activate").permitAll()
                .antMatchers("/api/v1/login").permitAll()   // Permit all requests without authentication
                .anyRequest().authenticated();
    }
}
